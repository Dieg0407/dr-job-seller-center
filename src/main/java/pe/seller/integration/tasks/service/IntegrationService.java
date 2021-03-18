package pe.seller.integration.tasks.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.seller.integration.domain.model.dto.RequestDTO;
import pe.seller.integration.domain.model.dto.RequestItemDTO;
import pe.seller.integration.domain.model.dto.SellerInfoDTO;
import pe.seller.integration.domain.model.entity.UrlConfiguration;
import pe.seller.integration.domain.repository.IUrlConfigurationRepository;
import pe.seller.integration.domain.service.IIntegrationService;
import pe.seller.integration.domain.service.ISaveInfoService;
import pe.seller.integration.domain.service.ISellerInfoService;
import java.util.ArrayList;
import java.util.stream.StreamSupport;

import static java.util.Optional.ofNullable;

@Log4j2
@Service
public class IntegrationService implements IIntegrationService {
    @Autowired ObjectMapper mapper;

    @Autowired IUrlConfigurationRepository repository;

    @Autowired ISellerInfoService sellerInfoService;
    @Autowired ISaveInfoService saveInfoService;

    @Override
    public boolean process(String rawMessage) {
        try {
            final var json = mapper.readTree(rawMessage);
            final var sellerInfo = sellerInfoService.findSellerData(json.get("accountName").textValue());
            final var request = createRequest(json, sellerInfo);
            final var configuration = repository.findById(1);

            saveInfoService.save(request, configuration.map(UrlConfiguration::getUrl).orElse("/url"));

            return true;
        }
        catch (Exception e) {
            log.error(e);
        }
        return false;
    }

    private RequestDTO createRequest(JsonNode json, SellerInfoDTO sellerInfo) {
        final var dto = new RequestDTO();

        dto.setMarketplaceId(json.get("id").textValue());
        dto.setSellerCorporateDocument(sellerInfo.getCorporateDocument());
        dto.setSellerCorporateName(sellerInfo.getCorporateName());
        dto.setSellerAddress(sellerInfo.getAddress());
        dto.setSellerPhone(sellerInfo.getPhone());
        dto.setSellerEmail(sellerInfo.getEmail());
        dto.setSellerUbigeo(sellerInfo.getUbigeo());

        if (json.get("clientProfileData") != null) {
            final var client = json.get("clientProfileData");
            dto.setClientDocumentType(textValue(client, "documentType"));
            dto.setClientDocument(textValue(client, "document"));
            dto.setClientFirstName(textValue(client, "firstName"));
            dto.setClientLastName(textValue(client, "lastName"));
            dto.setClientPhone(textValue(client, "phone"));
            dto.setClientEmail(textValue(client, "email"));
        }
        if (json.get("shippingData") != null) {
            final var shippingData = json.get("shippingData");
            if (shippingData.get("address") != null) {
                final var address = shippingData.get("address");

                dto.setClientAddressStreet(textValue(address, "street"));
                dto.setClientAddressNumber(textValue(address, "number"));
                dto.setClientAddressComplement(textValue(address, "complement"));
                dto.setClientAddressReference(textValue(address, "reference"));
                dto.setClientReceiverName(textValue(address, "receiverName"));
                dto.setClientUbigeo(textValue(address, "postalCode"));
                dto.setClientReceiverDocument(textValue(address, "receiverDocument"));
            }
        }
        final var items = StreamSupport
                .stream(json.get("items").spliterator(), false)
                .toArray(JsonNode[]::new);

        dto.setItems(new ArrayList<>());
        for(var item : items) {
            final var ref = new RequestItemDTO();

            ref.setId(textValue(item,"id"));
            ref.setDescription(textValue(item,"name"));
            ref.setQuantity(String.valueOf(intValue(item, "quantity")));
            ref.setWeight("");
            ref.setVolWeight("");
            ref.setPrice(String.valueOf(doubleValue(item, "price")));
            ref.setWidth("");
            ref.setHeight("");
            ref.setLenght("");

            dto.getItems().add(ref);
        }

        dto.setItemsQuantity(String.valueOf(items.length));
        dto.setOrderVolWeight("");
        dto.setOrderNumber("");
        dto.setCurrencyCode("");
        dto.setTotalValue(json.get("value").asDouble());

        return dto;
    }

    static String textValue(JsonNode node, String field) {
        return ofNullable(node.get(field)).map(JsonNode::textValue).orElse(null);
    }
    static Integer intValue(JsonNode node, String field) {
        return ofNullable(node.get(field)).map(JsonNode::intValue).orElse(0);
    }
    static Double doubleValue(JsonNode node, String field) {
        return ofNullable(node.get(field)).map(JsonNode::doubleValue).orElse(0.0);
    }
}

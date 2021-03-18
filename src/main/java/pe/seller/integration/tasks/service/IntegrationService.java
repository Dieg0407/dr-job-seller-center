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
import java.util.Arrays;
import java.util.stream.StreamSupport;

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
            dto.setClientDocumentType(client.get("documentType").textValue());
            dto.setClientDocument(client.get("document").textValue());
            dto.setClientFirstName(client.get("firstName").textValue());
            dto.setClientLastName(client.get("lastName").textValue());
            dto.setClientPhone(client.get("phone").textValue());
            dto.setClientEmail(client.get("email").textValue());
        }
        if (json.get("shippingData") != null) {
            final var shippingData = json.get("shippingData");
            if (shippingData.get("address") != null) {
                final var address = shippingData.get("address");

                dto.setClientAddressStreet(address.get("street").textValue());
                dto.setClientAddressNumber(address.get("number").textValue());
                dto.setClientAddressComplement(address.get("complement").textValue());
                dto.setClientAddressReference(address.get("reference").textValue());
                dto.setClientReceiverName(address.get("receiverName").textValue());
                dto.setClientUbigeo(address.get("postalCode").textValue());
                dto.setClientReceiverDocument(address.get("receiverDocument").textValue());
            }
        }
        final var items = StreamSupport
                .stream(json.get("items").spliterator(), false)
                .toArray(JsonNode[]::new);

        for(var item : items) {
            final var ref = new RequestItemDTO();

            ref.setId(item.get("id").textValue());
            ref.setDescription(item.get("name").textValue());
            ref.setQuantity(String.valueOf(item.get("quantity").intValue()));
            ref.setWeight("");
            ref.setVolWeight("");
            ref.setPrice(String.valueOf(item.get("price").doubleValue()));
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
        dto.setItems(new ArrayList<>());

        return dto;
    }
}

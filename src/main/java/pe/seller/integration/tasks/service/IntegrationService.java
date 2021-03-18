package pe.seller.integration.tasks.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.seller.integration.domain.model.dto.RequestDTO;
import pe.seller.integration.domain.model.dto.SellerInfoDTO;
import pe.seller.integration.domain.model.entity.UrlConfiguration;
import pe.seller.integration.domain.repository.IUrlConfigurationRepository;
import pe.seller.integration.domain.service.IIntegrationService;
import pe.seller.integration.domain.service.ISaveInfoService;
import pe.seller.integration.domain.service.ISellerInfoService;

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
        return new RequestDTO();
    }
}

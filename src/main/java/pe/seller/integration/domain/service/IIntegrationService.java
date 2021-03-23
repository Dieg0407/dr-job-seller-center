package pe.seller.integration.domain.service;

import com.fasterxml.jackson.databind.JsonNode;
import pe.seller.integration.domain.model.dto.IntegrationRequestDTO;

public interface IIntegrationService {
    boolean process(String rawMessage);
    boolean process(IntegrationRequestDTO message);
}

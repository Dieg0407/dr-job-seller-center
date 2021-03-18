package pe.seller.integration.tasks.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.seller.integration.domain.service.IIntegrationService;

@Service
public class IntegrationService implements IIntegrationService {

    @Autowired ObjectMapper mapper;

    @Override
    public boolean process(String rawMessage) {
        try {
            final var json = mapper.readTree(rawMessage);

        }
        catch (Exception e) {

        }
        return false;
    }
}

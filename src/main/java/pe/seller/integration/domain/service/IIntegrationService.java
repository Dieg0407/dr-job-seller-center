package pe.seller.integration.domain.service;

public interface IIntegrationService {
    boolean process(String rawMessage);
}

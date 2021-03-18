package pe.seller.integration.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.seller.integration.domain.model.entity.UrlConfiguration;

public interface IUrlConfigurationRepository extends JpaRepository<UrlConfiguration, Integer> {
}

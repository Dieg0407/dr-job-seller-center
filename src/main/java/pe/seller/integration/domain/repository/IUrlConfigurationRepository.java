package pe.seller.integration.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.seller.integration.domain.model.entity.UrlConfiguration;

import java.util.Optional;

@Repository
public interface IUrlConfigurationRepository extends JpaRepository<UrlConfiguration, Integer> {
    Optional<UrlConfiguration> findBySellerName(String sellerName);
}

package pe.seller.integration.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.seller.integration.domain.model.entity.SellerLog;

@Repository
public interface ISellerLogRepository extends JpaRepository<SellerLog, Integer> {
}

package dm.creditservice.repository;

import dm.creditservice.entity.CreditClaimOutBoxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CreditClaimOutBoxEntityRepository extends JpaRepository<CreditClaimOutBoxEntity, UUID> {
}
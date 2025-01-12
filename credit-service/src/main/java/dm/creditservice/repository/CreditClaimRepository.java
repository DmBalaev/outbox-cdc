package dm.creditservice.repository;

import dm.creditservice.entity.CreditClaimEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CreditClaimRepository extends JpaRepository<CreditClaimEntity, UUID> {
}

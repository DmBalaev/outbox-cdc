package dm.creditservice.payload;

import dm.creditservice.entity.ClaimStatus;
import dm.creditservice.entity.CreditClaimEntity;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for {@link CreditClaimEntity}
 */
@Value
public class CreditClaimResponse implements Serializable {
    UUID clientId;
    BigDecimal amount;
    Integer term;
    ClaimStatus status;
    LocalDate creationAt;
    LocalDate updateAt;
}
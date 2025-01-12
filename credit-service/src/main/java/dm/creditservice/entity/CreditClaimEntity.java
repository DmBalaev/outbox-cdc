package dm.creditservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = CreditClaimEntity.CREDIT_CLAIM_TABLE_NAME)
public class CreditClaimEntity {
    public static final String CREDIT_CLAIM_TABLE_NAME = "credit_claim";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID clientId;
    private BigDecimal amount;
    private Integer term;
    @Enumerated(EnumType.STRING)
    private ClaimStatus status;
    private LocalDate creationAt;
    private LocalDate updateAt;
}

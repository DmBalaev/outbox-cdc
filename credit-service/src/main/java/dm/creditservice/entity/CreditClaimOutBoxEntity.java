package dm.creditservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = CreditClaimOutBoxEntity.CREDIT_CLAIM_OUTBOX_TABLE_NAME)
public class CreditClaimOutBoxEntity {
    public static final String CREDIT_CLAIM_OUTBOX_TABLE_NAME = "credit_claim_outbox";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ClaimStatus eventType;

    private UUID claimId;

    @Column(name = "event_payload", columnDefinition = "TEXT")
    private String eventPayload;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private ProcessingStatus processingStatus;
}

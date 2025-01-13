package dm.creditservice.event;

import dm.creditservice.entity.ClaimStatus;

import java.util.UUID;

public record CreditClaimEvent(
        UUID claimId,
        ClaimStatus claimStatus
) {
}

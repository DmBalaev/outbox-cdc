package dm.creditservice.service;

import dm.creditservice.entity.ClaimStatus;
import dm.creditservice.entity.CreditClaimEntity;
import dm.creditservice.payload.CreateCreditClaimRequest;

import java.util.List;
import java.util.UUID;

public interface CreditClaimService {

    CreditClaimEntity createCreditClaim(CreateCreditClaimRequest createCreditClaimRequest);

    List<CreditClaimEntity> getCreditClaims();

    CreditClaimEntity updateCreditClaimStatus(UUID claimId, ClaimStatus claimStatus);
}

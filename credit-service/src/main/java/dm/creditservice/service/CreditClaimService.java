package dm.creditservice.service;

import dm.creditservice.entity.ClaimStatus;
import dm.creditservice.entity.CreditClaimEntity;

import java.util.List;

public interface CreditClaimService {

    CreditClaimEntity createCreditClaim(CreditClaimEntity creditClaim);

    List<CreditClaimEntity> getCreditClaims();

    CreditClaimEntity updateCreditClaimStatus(ClaimStatus claimStatus);
}

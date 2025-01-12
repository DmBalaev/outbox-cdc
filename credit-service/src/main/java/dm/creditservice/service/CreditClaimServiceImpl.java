package dm.creditservice.service;

import dm.creditservice.entity.ClaimStatus;
import dm.creditservice.entity.CreditClaimEntity;
import dm.creditservice.repository.CreditClaimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditClaimServiceImpl implements CreditClaimService {
    private final CreditClaimRepository repository;

    @Override
    public CreditClaimEntity createCreditClaim(CreditClaimEntity creditClaim) {
        return null;
    }

    @Override
    public List<CreditClaimEntity> getCreditClaims() {
        return List.of();
    }

    @Override
    public CreditClaimEntity updateCreditClaimStatus(ClaimStatus claimStatus) {
        return null;
    }
}

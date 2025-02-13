package dm.creditservice.service;

import dm.creditservice.entity.ClaimStatus;
import dm.creditservice.entity.CreditClaimEntity;
import dm.creditservice.event.CreditClaimEvent;
import dm.creditservice.exception.ResourceNotFound;
import dm.creditservice.payload.CreateCreditClaimRequest;
import dm.creditservice.publisher.CreditClaimPublisher;
import dm.creditservice.repository.CreditClaimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreditClaimServiceImpl implements CreditClaimService {
    private final CreditClaimRepository claimRepository;
    private final CreditClaimPublisher creditClaimPublisher;

    @Override
    public CreditClaimEntity createCreditClaim(CreateCreditClaimRequest request) {
        CreditClaimEntity creditClaimEntity = CreditClaimEntity.builder()
                .amount(request.amount())
                .term(request.termInMonths())
                .status(ClaimStatus.CREATED)
                .creationAt(LocalDate.now())
                .build();
        return claimRepository.save(creditClaimEntity);
    }

    @Override
    public List<CreditClaimEntity> getCreditClaims() {
        return claimRepository.findAll();
    }

    @Override
    public CreditClaimEntity updateCreditClaimStatus(UUID claimId, ClaimStatus claimStatus) {
        CreditClaimEntity claimEntity = claimRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFound("Credit Claim not found"));
        claimEntity.setStatus(claimStatus);
        claimEntity.setUpdateAt(LocalDate.now());

        //При обновлении отправляем в кафку сообщение о смене статуса
        CreditClaimEvent event = new CreditClaimEvent(claimEntity.getId(), claimStatus);
        creditClaimPublisher.sendStatusUpdate(CreditClaimPublisher.CREDIT_CLAIM_TOPIC, event);

        return claimRepository.save(claimEntity);
    }
}

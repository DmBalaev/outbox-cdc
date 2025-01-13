package dm.creditservice.listener;

import dm.creditservice.entity.CreditClaimEntity;
import dm.creditservice.event.CreditClaimEvent;
import dm.creditservice.publisher.CreditClaimPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class StatusEventListener {
    private final CreditClaimPublisher creditClaimPublisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendToNotifyService(CreditClaimEntity claim) {
        CreditClaimEvent event = new CreditClaimEvent(claim.getId(), claim.getStatus());
        creditClaimPublisher.sendStatusUpdate(event);
    }
}
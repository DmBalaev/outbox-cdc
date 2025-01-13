package dm.creditservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dm.creditservice.entity.ClaimStatus;
import dm.creditservice.entity.CreditClaimOutBoxEntity;
import dm.creditservice.event.CreditClaimEvent;
import dm.creditservice.publisher.CreditClaimPublisher;
import dm.creditservice.repository.CreditClaimOutBoxEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutBoxService {
    private final CreditClaimPublisher creditClaimPublisher;
    private final CreditClaimOutBoxEntityRepository outboxRepository;

    @Scheduled(fixedRateString = "${configuration.scheduled}")
    public void eventProcessing() {
        List<CreditClaimOutBoxEntity> listOfOutboxEventEntities = new ArrayList<>();
        outboxRepository.findAll().forEach(listOfOutboxEventEntities::add);
        log.info("Number of outbox events: {}", listOfOutboxEventEntities.size());

        if (!listOfOutboxEventEntities.isEmpty()) {
            for (CreditClaimOutBoxEntity outboxEventEntity : listOfOutboxEventEntities) {
                ClaimStatus eventType = outboxEventEntity.getEventType();
                if (eventType != null) {
                    CreditClaimEvent event = new CreditClaimEvent(outboxEventEntity.getClaimId(), eventType);
                    creditClaimPublisher.sendStatusUpdate(event);
                }
                outboxRepository.deleteById(outboxEventEntity.getId());
            }
        }
    }
}

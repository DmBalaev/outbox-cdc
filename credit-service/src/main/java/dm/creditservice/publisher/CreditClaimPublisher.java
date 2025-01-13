package dm.creditservice.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditClaimPublisher {
    public static final String CREDIT_CLAIM_TOPIC = "credit-claim";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendStatusUpdate(String topic, Object message) {
        kafkaTemplate.send(topic, message);
    }
}

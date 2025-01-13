package dm.notificationservice.listner;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CreditClaimStatusListner {

    @KafkaListener(topics = "credit-claim", groupId = "notification-group")
    public void listen(ConsumerRecord<String, String> record) {
        sendNotification(record.value());
    }

    private void sendNotification(String message) {
        System.out.printf("Sending notification: Claim updated status %s", message);
    }

}

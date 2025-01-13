package dm.creditservice.listener;

import dm.creditservice.entity.ClaimStatus;
import dm.creditservice.event.CreditClaimEvent;
import dm.creditservice.publisher.CreditClaimPublisher;
import io.debezium.config.Configuration;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static io.debezium.data.Envelope.FieldName.OPERATION;
import static io.debezium.data.Envelope.Operation;

@Slf4j
@Component
public class DebeziumListener {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine;
    private final CreditClaimPublisher creditClaimPublisher;

    public DebeziumListener(Configuration postgresConnector, CreditClaimPublisher creditClaimPublisher) {
        this.debeziumEngine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
                .using(postgresConnector.asProperties())
                .notifying(this::handleEvent)
                .build();
        this.creditClaimPublisher = creditClaimPublisher;
    }

    private void handleEvent(RecordChangeEvent<SourceRecord> sourceRecordRecordChangeEvent) {
        SourceRecord sourceRecord = sourceRecordRecordChangeEvent.record();
        Struct sourceRecordChangeValue = (Struct) sourceRecord.value();
        log.info("Key = '{}' value = '{}'", sourceRecord.key(), sourceRecord.value());

        if (sourceRecordChangeValue != null) {
            Operation operation = Operation.forCode((String) sourceRecordChangeValue.get(OPERATION));

            if (operation == Operation.UPDATE) {
                Struct after = (Struct) sourceRecordChangeValue.get("after");
                String newStatus = after.getString("status");
                UUID id = UUID.fromString(after.getString("id"));

                CreditClaimEvent event = new CreditClaimEvent(id, ClaimStatus.valueOf(newStatus));
                creditClaimPublisher.sendStatusUpdate(event);
            }
        }
    }

    @PostConstruct
    private void start() {
        this.executor.execute(debeziumEngine);
    }

    @PreDestroy
    private void stop() throws IOException {
        if (this.debeziumEngine != null) {
            this.debeziumEngine.close();
        }
    }
}

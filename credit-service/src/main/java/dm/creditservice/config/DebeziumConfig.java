package dm.creditservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static dm.creditservice.publisher.CreditClaimPublisher.CREDIT_CLAIM_TOPIC;

@Configuration
public class DebeziumConfig {
    private static final String CREDIT_CLAIM_TABLE_NAME = "public.credit_claim";

    @Value("${credit.datasource.host}")
    private String creditDbHost;

    @Value("${credit.datasource.database}")
    private String customerDbName;

    @Value("${credit.datasource.port}")
    private String creditDbPort;

    @Value("${credit.datasource.username}")
    private String creditDbUsername;

    @Value("${credit.datasource.password}")
    private String creditDbPassword;

    @Bean
    public io.debezium.config.Configuration studentConnector() {

        return io.debezium.config.Configuration.create().
                with("name", "credit-claim-postgres-connector").
                with("connector.class", "io.debezium.connector.postgresql.PostgresConnector").
                with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore").
                with("offset.storage.file.filename", "/tmp/offsets.dat").
                with("offset.flush.interval.ms", "60000").
                with("database.hostname", creditDbHost).
                with("database.port", creditDbPort).
                with("database.user", creditDbUsername).
                with("database.password", creditDbPassword).
                with("database.dbname", customerDbName).
                with("table.whitelist", CREDIT_CLAIM_TABLE_NAME).
                with("topic.prefix", CREDIT_CLAIM_TOPIC).
                with("plugin.name", "pgoutput").
                with("schema.history.internal", "io.debezium.storage.file.history.FileSchemaHistory").
                with("schema.history.internal.file.filename", "/tmp/schemahistory.dat").build();
    }
}

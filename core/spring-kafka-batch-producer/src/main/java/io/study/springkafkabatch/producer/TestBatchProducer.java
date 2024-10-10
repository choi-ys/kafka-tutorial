package io.study.springkafkabatch.producer;

import io.study.kafka.domain.message.TestMessage;
import io.study.springkafkabatch.config.kafka.BulkKafkaTemplate;
import io.study.springkafkabatch.utils.KafkaTopicConstants;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestBatchProducer {

    private static final int PRODUCE_DATA_BULK_SIZE = 1000;
    private static final String TEST_BATCH_TOPIC = "test-batch-topic";
    private final BulkKafkaTemplate<String, TestMessage> batchKafkaTemplate;

    public void sendMessages(TestMessage message) {
        List<ProducerRecord<String, TestMessage>> records = IntStream.range(0, PRODUCE_DATA_BULK_SIZE)
            .<ProducerRecord<String, TestMessage>>mapToObj(i -> new ProducerRecord<>(TEST_BATCH_TOPIC, null, message))
            .toList();

        CompletableFuture.runAsync(() -> {
            batchKafkaTemplate.sendBulk(records);
            log.info("Batch Producing records is Transactional -> [topic : {} -> size : {}]",
                KafkaTopicConstants.TEST_BATCH_TOPIC,
                records.size()
            );
        });
        log.info("Producing records...");
    }

}

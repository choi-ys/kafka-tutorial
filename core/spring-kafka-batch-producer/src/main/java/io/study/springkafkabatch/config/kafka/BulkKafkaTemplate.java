package io.study.springkafkabatch.config.kafka;

import static io.study.springkafkabatch.logger.PartitionMessageLogger.collectProducedRecordMetadata;
import static io.study.springkafkabatch.logger.PartitionMessageLogger.logPartitionOffsets;

import io.study.springkafkabatch.logger.PartitionMessageLogger;
import java.util.List;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Slf4j
public class BulkKafkaTemplate<K, V> extends KafkaTemplate<K, V> {

    public BulkKafkaTemplate(ProducerFactory<K, V> producerFactory) {
        super(producerFactory);
    }

    /**
     * 복수개의 메시지를 트랜잭션 처리 발행합니다.
     * <p>
     * - acks = -1(all), transactional.id 설정을 통해 트랜잭션 내에서 처리됩니다. {@link BatchProducerConfig}
     * <p>
     * - 메시지 발행 시, key based 파티셔닝이 아닌 hash based 파티셔닝을 적용합니다. {@link HashBasedPartitioner}
     * <p>
     * - 발행된 메시지의 파티션과 오프셋 정보는 별도 로거를 이용하여 출력됩니다. {@link PartitionMessageLogger}
     *
     * @param records 토픽 정보가 포함된 복수개의 발행 대상 메세지
     */
    public void sendBulk(List<ProducerRecord<K, V>> records) {
        executeInTransaction(operations -> {
            produceRecords(operations, records);
            logPartitionOffsets();
            return null;
        });
    }

    // 복수개의 메시지를 발행
    private void produceRecords(KafkaOperations<K, V> operations, List<ProducerRecord<K, V>> records) {
        IntStream.range(0, records.size())
            .forEach(i -> sendRecord(operations, records.get(i)));
    }

    // 개별 레코드 발행 및 발행 결과를 이용한 추가 처리(발행 파티션 정보 수집)
    private void sendRecord(
        KafkaOperations<K, V> operations,
        ProducerRecord<K, V> record
    ) {
        operations.send(record).thenAccept(result -> {
            if (result != null) {
                collectProducedRecordMetadata(result.getRecordMetadata());
            }
        }).exceptionally(ex -> {
            log.error("Failed to send message: {}", record.value(), ex);
            return null;
        });
    }

}

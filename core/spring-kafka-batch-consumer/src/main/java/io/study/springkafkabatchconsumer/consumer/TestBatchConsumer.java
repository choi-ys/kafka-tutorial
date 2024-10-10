package io.study.springkafkabatchconsumer.consumer;

import io.study.kafka.domain.message.TestMessage;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestBatchConsumer {

    private static final String TEST_BATCH_TOPIC = "test-batch-topic";
    private static final String TEST_BATCH_CONSUMER_GROUP = "test-batch-consumer-group";

    @KafkaListener(
        topics = {TEST_BATCH_TOPIC},
        groupId = TEST_BATCH_CONSUMER_GROUP,
        containerFactory = "batchListenerFactory"
    )
    public void acceptBatch(List<ConsumerRecord<String, TestMessage>> records) {
        printLog(records);
    }

    private void printLog(List<ConsumerRecord<String, TestMessage>> records) {
        // 파티션별로 그룹화하여 메시지 개수 및 오프셋을 저장
        Map<Integer, List<Long>> partitionOffsets = records.stream()
            .collect(Collectors.groupingBy(
                ConsumerRecord::partition, // 파티션별로 그룹화
                Collectors.mapping(ConsumerRecord::offset, Collectors.toList()) // 오프셋 수집
            ));

        // 파티션별 메시지 개수 및 오프셋 출력
        partitionOffsets.forEach((partition, offsets) -> {
            log.info("- Consume records: [topic : {} -> Partition: {}, Message count: {}]",
                TEST_BATCH_TOPIC,
                partition,
                offsets.size()
            );
        });
    }

}

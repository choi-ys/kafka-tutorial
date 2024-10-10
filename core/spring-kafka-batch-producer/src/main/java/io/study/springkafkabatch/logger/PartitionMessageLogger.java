package io.study.springkafkabatch.logger;

import static org.apache.tomcat.util.json.JSONParserConstants.ZERO;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;

@Slf4j
public class PartitionMessageLogger {

    private static final Map<Integer, AtomicLong> partitionMessageCount = new ConcurrentHashMap<>(); // 파티션별 메시지 카운트

    // 파티션에 발행된 메세지 수 수집
    public static void collectProducedRecordMetadata(RecordMetadata recordMetadata) {
        partitionMessageCount
            .computeIfAbsent(
                recordMetadata.partition(),
                messageCount -> new AtomicLong(ZERO)
            )
            .incrementAndGet();
    }

    // 각 파티션별 수집된 발행 메시지 수 출력
    public static void logPartitionOffsets() {
        partitionMessageCount.forEach((partition, producedRecordCount) ->
            log.info("  ├── [Partition: {}] : Produced messages : {}", partition, producedRecordCount)
        );
        partitionMessageCount.clear();
    }

}

package io.study.springkafkabatch.config.kafka;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.record.CompressionType;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Slf4j
@Configuration
public class BatchProducerConfig<K, V> {

    /**
     * 일괄 메세지 발행을 위한 Kafka ProducerFactory 설정 초기화
     *
     * @param kafkaProperties kafka.yml
     * @return {@code ProducerFactory} Batch Producing 설정이 완료된 ProducerFactory
     */
    @Bean
    public ProducerFactory<K, V> batchProducerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> props = initDefaultProducerConfig(kafkaProperties);
        addDetailProducerConfig(props);

        return new DefaultKafkaProducerFactory<>(props);
    }

    /**
     * Producer 기본 설정 구성
     *
     * @param kafkaProperties Kafka.yml
     * @return {@code props} 초기 Producer 설정 정보 Map
     * @Note {@code BOOTSTRAP_SERVERS_CONFIG} Kafka Cluster 정보 설정
     * @Note {@code PARTITIONER_CLASS_CONFIG} 메시지 발행 시 Hash 기반 Partitioning 전략 설정
     */
    private Map<String, Object> initDefaultProducerConfig(KafkaProperties kafkaProperties) {
        return Map.of(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers(),
            ProducerConfig.PARTITIONER_CLASS_CONFIG, HashBasedPartitioner.class.getName()
        );
    }

    private void addDetailProducerConfig(Map<String, Object> props) {
        addSerializerConfig(props);
        addBatchConfig(props);
        addExactlyOnceConfig(props);
        addRetryConfig(props);
    }

    /**
     * 메시지 직렬화 관련 설정
     *
     * @param props Producer 설정 정보 Map  {@value  }
     * @Note {@code VALUE_SERIALIZER_CLASS_CONFIG} 메세지 직렬화 방식을 Json 형식으로 설정합니다.
     */
    private void addSerializerConfig(Map<String, Object> props) {
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    }

    /**
     * 배치 관련 설정을 추가합니다.
     *
     * @param props Producer 설정 정보 Map 설정합니다.
     * @apiNote sender가 1회 전송하는 메시지의 크기를(batch.size) 64kb로 설정하며 메시지 발행 지연 시, 메시지 대기 공간의(buffer.memory) 크기를 32MB로 설정합니다.
     * 1회 전송을 위한 메시지 누적 시간은(linger.ms) 50ms로 설정합니다.
     * @Note {@code COMPRESSION_TYPE_CONFIG} 메시지 압축 방식 설정 : gzip
     * @Note {@code BUFFER_MEMORY_CONFIG} 전송 대기 메시지를 저장하는 buffer memory 크기 설정 : 32MB(기본값)
     * @Note {@code LINGER_MS_CONFIG} 일괄 메시지를 구성하기까지의 대기 시간을 50ms로 설정합니다.
     * @Note {@code BATCH_SIZE_CONFIG} 일괄로 전송할 메시지의 최대 크기를 64KB로 설정합니다.
     * @Note {@code TRANSACTIONAL_ID_CONFIG} 일괄 메시지 발행 시 트랜잭션 기능을 활성화 합니다.
     * @Note {@code MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION} 일괄 메시지 발행 시 트랜잭션 기능을 활성화 합니다.
     */
    private void addBatchConfig(Map<String, Object> props) {
        final String TRANSACTIONAL_BATCH_PRODUCER = "transactional-batch-producer";
        final int _32_MB = 32 * 1024 * 1024;
        final int _64_KB = 32 * 1024;
        final int _50_MS = 50;

        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, CompressionType.GZIP.name);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, _32_MB);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, _64_KB);
        props.put(ProducerConfig.LINGER_MS_CONFIG, _50_MS);
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, TRANSACTIONAL_BATCH_PRODUCER);
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
    }

    /**
     * 메시지 발행 시 Exactly-Once 관련 설정을 추가합니다.
     *
     * @param props Producer 설정 정보 Map
     * @Note {@code ENABLE_IDEMPOTENCE_CONFIG} 동일 메시지 중복 발행 방지 설정
     * @Note {@code ACKS_CONFIG} 메세지 수신 확인 설정
     * @Note {@code MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION}
     */
    private void addExactlyOnceConfig(Map<String, Object> props) {
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        props.put(ProducerConfig.ACKS_CONFIG, Acks.ALL.getOption());
    }

    /**
     * 메시지 발행 시 Exactly-Once 관련 설정을 추가합니다.
     *
     * @param props Producer 설정 정보 Map
     * @Note {@code RETRIES_CONFIG} 메시지 전송 실패 시 재시도 활성화
     * @Note {@code DELIVERY_TIMEOUT_MS_CONFIG} 타임아웃 시간 설정
     * @Note {@code REQUEST_TIMEOUT_MS_CONFIG} 요청 타임아웃 시간
     * @Note {@code RETRY_BACKOFF_MS_CONFIG} 재시도 간 대기 시간
     */
    private void addRetryConfig(Map<String, Object> props) {
        Map<String, Object> exactlyOnceProps = Stream.of(
            Map.entry(ProducerConfig.RETRIES_CONFIG, true),
            Map.entry(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 120_000),
            Map.entry(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 30_000),
            Map.entry(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1_000)
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        props.putAll(exactlyOnceProps);
    }

    @Bean
    public KafkaTemplate<K, V> batchKafkaTemplate(KafkaProperties kafkaProperties) {
        return new KafkaTemplate<>(batchProducerFactory(kafkaProperties));
    }

    @Bean
    public BulkKafkaTemplate<K, V> bulkKafkaTemplate(KafkaProperties kafkaProperties) {
        return new BulkKafkaTemplate<>(batchProducerFactory(kafkaProperties));
    }

}

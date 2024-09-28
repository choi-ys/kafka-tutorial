package io.study.kafka.springkafka.config.kafka.producer;

import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class RealTimeProducerConfig {

    private static final String MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION = "max-in-flight-requests-per-connection";
    private static final String LINGER_MS = "linger-ms";
    private static final String ENABLE_IDEMPOTENCE = "enable-idempotence";

    /**
     * 실시간 메시지 발행을 위한 Kafka ProducerFactory 설정 초기화
     *
     * @param kafkaProperties kafka.yml
     * @return {@code ProducerFactory} 실시간 메시지 발행 환경 설정이 구성된 ProducerFactory
     * @Note {@code BOOTSTRAP_SERVERS_CONFIG} Kafka Cluster 정보
     * @Note {@code KEY_SERIALIZER_CLASS_CONFIG} 메세지 Key 직렬화 방식 : String (기본값)
     * @Note {@code VALUE_SERIALIZER_CLASS_CONFIG} 메세지 Value 직렬화 방식 : Json
     * @Note {@code COMPRESSION_TYPE_CONFIG} 메시지 압축 방식 : none
     * @Note {@code BATCH_SIZE_CONFIG} 일괄로 전송할 메시지의 최대 크기 : 16,384B (16KB)
     * @Note {@code LINGER_MS_CONFIG} 일괄 메시지를 구성하기까지의 대기 시간 : 0ms
     * @Note {@code MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION} 특정 브로커에 동시 전송 가능한 요청 수 : 5
     * @Note {@code ACKS_CONFIG} 브로커의 메시지 수신 여부 확인 : 1 (Reader partition only)
     * @Note {@code ENABLE_IDEMPOTENCE_CONFIG} 메시지 중복 방지 : true
     * @Note {@code RETRIES_CONFIG} 메시지 재 발행 시도 횟수 : 0
     */
    @Bean
    public ProducerFactory<String, Object> kafkaProducerFactory(KafkaProperties kafkaProperties) {
        Producer producer = kafkaProperties.getProducer();
        Map<String, String> properties = producer.getProperties();

        Map<String, Object> props = Map.of(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers(),
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producer.getKeySerializer(),
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producer.getValueSerializer(),
            ProducerConfig.COMPRESSION_TYPE_CONFIG, producer.getCompressionType(),
            ProducerConfig.BATCH_SIZE_CONFIG, producer.getBatchSize(),
            ProducerConfig.LINGER_MS_CONFIG, properties.get(LINGER_MS),
            ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, properties.get(MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION),
            ProducerConfig.ACKS_CONFIG, producer.getAcks(),
            ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, properties.get(ENABLE_IDEMPOTENCE),
            ProducerConfig.RETRIES_CONFIG, producer.getRetries()
        );

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, ?> kafkaTemplate(KafkaProperties kafkaProperties) {
        return new KafkaTemplate<>(kafkaProducerFactory(kafkaProperties));
    }

}

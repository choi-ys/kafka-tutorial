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
public class RealTimeProducerConfig<K, V> {

    private static final String BATCH_SIZE = "batch-size";
    private static final String MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION = "max-in-flight-requests-per-connection";
    private static final String LINGER_MS = "linger-ms";

    /**
     * 지연시간이 낮은 메시지 발행을 위한 Kafka ProducerFactory 설정 초기화
     *
     * @param kafkaProperties kafka.yml
     * @return {@code ProducerFactory} 실시간 메시지 발행 환경이 설정된 ProducerFactory
     * @Note {@code BOOTSTRAP_SERVERS_CONFIG} 메시지를 발행할 Kafka Cluster 정보
     * @Note {@code KEY_SERIALIZER_CLASS_CONFIG} StringSerializer를 이용한 Key 직렬화
     * @Note {@code VALUE_SERIALIZER_CLASS_CONFIG} JsonSerializer를 이용한 value 직렬화
     * @Note {@code COMPRESSION_TYPE_CONFIG} 메시지 압축 방식 미 적용
     * @Note {@code BATCH_SIZE_CONFIG} 일괄 전송을 위해 한번에 묶여서 보내질 수 있는 메시지의 최대 크기를 16KB로 설정
     * @Note {@code LINGER_MS_CONFIG} 일괄 메시지 구성까지의 대기 시간 없음 (즉시 전송)
     * @Note {@code MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION} 특정 브로커에 동시 전송 가능한 요청 수 : 3
     * @Note {@code ACKS_CONFIG} 브로커의 메시지 수신 여부 확인 시 Leader partition의 메시지 수신 여부만 확인
     * @Note {@code RETRIES_CONFIG} 메시지 전송 실패 시 재시도 횟수 : 1
     */
    @Bean
    public ProducerFactory<K, V> kafkaProducerFactory(KafkaProperties kafkaProperties) {
        Producer producer = kafkaProperties.getProducer();
        Map<String, String> properties = producer.getProperties();

        Map<String, Object> props = Map.of(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers(),
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producer.getKeySerializer(),
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producer.getValueSerializer(),
            ProducerConfig.COMPRESSION_TYPE_CONFIG, producer.getCompressionType(),
            ProducerConfig.BATCH_SIZE_CONFIG, properties.get(BATCH_SIZE),
            ProducerConfig.LINGER_MS_CONFIG, properties.get(LINGER_MS),
            ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, properties.get(MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION),
            ProducerConfig.ACKS_CONFIG, producer.getAcks(),
            ProducerConfig.RETRIES_CONFIG, producer.getRetries()
        );

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<K, V> kafkaTemplate(
        KafkaProperties kafkaProperties,
        KafkaProducerListener<K, V> kafkaProducerListener
    ) {
        KafkaTemplate<K, V> kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory(kafkaProperties));
        kafkaTemplate.setProducerListener(kafkaProducerListener);

        return kafkaTemplate;
    }

}

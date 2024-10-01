package io.study.kafka.springkafka.config.kafka.consumer;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Consumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@RequiredArgsConstructor
public class RealTimeConsumerConfig {

    @Value("${spring.kafka.consumer.fetch-max-wait}")
    private int fetchMaxWaitMs;

    @Value("${spring.kafka.consumer.fetch-min-size}")
    private int fetchMinSize;

    @Value("${spring.kafka.consumer.properties.spring.json.trusted.packages}")
    private String trustedPackagesRange;

    @Value("${spring.kafka.consumer.properties.allow.auto.create.topic}")
    private boolean isAutoCreateTopic;

    /**
     * 지연시간이 낮은 메시지 수신을 위한 Kafka ConsumerFactory 설정 초기화
     *
     * @param kafkaProperties kafka.yml
     * @return {@code ConsumerFactory} 실시간 메시지 소비 환경이 설정된 ConsumerFactory
     * @Note {@code BOOTSTRAP_SERVERS_CONFIG} 메시지를 소비할 Kafka Cluster 정보
     * @Note {@code KEY_DESERIALIZER_CLASS_CONFIG} StringDeserializer를 이용한 Key 역직렬화
     * @Note {@code VALUE_DESERIALIZER_CLASS_CONFIG} JsonDeserializer를 이용한 Value 역직렬화
     * @Note {@code TRUSTED_PACKAGES} 역직렬화 시 Hedaer Type 의 모든 Package를 신뢰
     * @Note {@code MAX_POLL_RECORDS_CONFIG} 한번의 poll() 호출로 쓰레드가 최대 100개의 메세지 소비
     * @Note {@code FETCH_MIN_BYTES_CONFIG} fetch할 최소 데이터 크기를 1MB로 설정, 컨슈머는 해당 데이터 크기가 충족될 때 까지 대기
     * @Note {@code FETCH_MAX_WAIT_MS_CONFIG} FETCH_MIN_BYTES 미만이거나 MAX_POLL_RECORDS_CONFIG 미만인 경우 최대 50ms 대기
     * @Note {@code ALLOW_AUTO_CREATE_TOPICS_CONFIG} topic 자동 생성 미 허용
     * @Note {@code AUTO_OFFSET_RESET_CONFIG} Consumer의 offset 정보가 존재 하지 않는 경우 가장 마지막 offset 부터 소비
     * @Note {@code ENABLE_AUTO_COMMIT_CONFIG} 컨슈머가 주기적으로 offset 자동 커밋
     */
    @Bean
    public ConsumerFactory<String, Object> consumerFactory(KafkaProperties kafkaProperties) {
        Consumer consumer = kafkaProperties.getConsumer();

        Map<String, Object> props = Map.of(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers(),
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumer.getKeyDeserializer(),
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumer.getValueDeserializer(),
            JsonDeserializer.TRUSTED_PACKAGES, trustedPackagesRange,
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG, consumer.getMaxPollRecords(),
            ConsumerConfig.FETCH_MIN_BYTES_CONFIG, fetchMinSize,
            ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, fetchMaxWaitMs,
            ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, isAutoCreateTopic,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumer.getAutoOffsetReset(),
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, consumer.getEnableAutoCommit()
        );

        return new DefaultKafkaConsumerFactory<>(props);
    }

    /**
     * @param kafkaProperties kafka.yml
     * @param consumerFactory 병렬 메시지 소비 환경이 설정된 ConsumerFactory
     * @return setConcurrency: 서로 다른 파티션에서 메시지를 병렬로 가져올 스레드 수
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerFactory(
        KafkaProperties kafkaProperties,
        ConsumerFactory<String, Object> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(kafkaProperties.getListener().getConcurrency());
        factory.setBatchListener(false);

        return factory;
    }

}

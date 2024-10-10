package io.study.springkafkabatchconsumer.config.kafka;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@RequiredArgsConstructor
@Configuration
public class BatchConsumerConfig {

    @Bean
    public ConsumerFactory<String, Object> batchConsumerFactory(KafkaProperties kafkaProperties) {
        Map<String, Object> props = Map.of(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers(),
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsumer().getKeyDeserializer(),
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getConsumer().getValueDeserializer(),
            JsonDeserializer.TRUSTED_PACKAGES, "*",
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 200, // 최대 200개 내에서 consuming
            ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 10_000 // 최대 1분 내에서 consuming
        );

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> batchListenerFactory(
        ConsumerFactory<String, Object> batchConsumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(batchConsumerFactory);
        factory.setConcurrency(3);
        factory.setBatchListener(true);

        return factory;
    }

}

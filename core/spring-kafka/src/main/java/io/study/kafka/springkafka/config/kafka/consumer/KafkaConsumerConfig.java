package io.study.kafka.springkafka.config.kafka.consumer;

import static io.study.kafka.springkafka.config.kafka.consumer.ConsumerConfigConstants.ALLOW_AUTO_CREATE_TOPICS_FALSE;
import static io.study.kafka.springkafka.config.kafka.consumer.ConsumerConfigConstants.CONSUME_OFFSET_LATEST;
import static io.study.kafka.springkafka.config.kafka.consumer.ConsumerConfigConstants.TRUSTED_ALL_PACKAGES;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
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
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, Object> consumerFactory(KafkaProperties kafkaProperties) {
        Consumer consumer = kafkaProperties.getConsumer();

        Map<String, Object> props = Map.of(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers(),
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumer.getKeyDeserializer(),
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumer.getValueDeserializer(),
            JsonDeserializer.TRUSTED_PACKAGES, TRUSTED_ALL_PACKAGES.getValue(),
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, CONSUME_OFFSET_LATEST.getValue(),
            ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, ALLOW_AUTO_CREATE_TOPICS_FALSE.getValue()
        );

        return new DefaultKafkaConsumerFactory<>(props);
    }

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

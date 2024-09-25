package io.study.kafka.springkafka.config.kafka;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class KafkaPropertiesConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.kafka")
    public KafkaProperties kafkaProperties() {
        return new KafkaProperties();
    }

}

package io.study.springkafkabatch.config.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class KafkaRetryTemplate {

    @Bean
    public RetryTemplate retryTemplate() {
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(SimpleRetryPolicy.DEFAULT_MAX_ATTEMPTS);

        return RetryTemplate.builder()
            .customPolicy(retryPolicy)
            .build();
    }

}

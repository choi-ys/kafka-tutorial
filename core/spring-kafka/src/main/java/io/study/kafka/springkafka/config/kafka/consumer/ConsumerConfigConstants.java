package io.study.kafka.springkafka.config.kafka.consumer;

import lombok.Getter;

@Getter
public enum ConsumerConfigConstants {
    TRUSTED_ALL_PACKAGES("*"),
    CONSUME_OFFSET_LATEST("latest"),
    ALLOW_AUTO_CREATE_TOPICS_FALSE("false");

    private final String value;

    ConsumerConfigConstants(String value) {
        this.value = value;
    }
}

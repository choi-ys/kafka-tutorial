package io.study.springkafkabatch.config.kafka;

import lombok.Getter;

@Getter
public enum Acks {
    NO_ACK("1"), LEADER_ACK("0"), ALL("-1");

    private final String option;

    Acks(String option) {
        this.option = option;
    }
}

package io.study.kafka.springkafka.producer;

import static io.study.kafka.springkafka.utils.KafkaTopicConstants.TEST_TOPIC;

import io.study.kafka.domain.message.TestMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestProducer {

    private final KafkaTemplate<String, TestMessage> kafkaTemplate;

    public void send(TestMessage message) {
        kafkaTemplate.send(TEST_TOPIC, String.valueOf(message.id()), message);
    }

}

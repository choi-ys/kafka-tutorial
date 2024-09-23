package io.study.kafka.springkafka.consumer;

import static io.study.kafka.springkafka.utils.KafkaTopicConstants.TEST_CONSUMER_GROUP;
import static io.study.kafka.springkafka.utils.KafkaTopicConstants.TEST_TOPIC;

import io.study.kafka.domain.message.TestMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestConsumer {

    @KafkaListener(topics = {TEST_TOPIC}, groupId = TEST_CONSUMER_GROUP)
    public void accept(ConsumerRecord<String, TestMessage> record) {
        log.info("Received record: {}", record.value());
    }

}

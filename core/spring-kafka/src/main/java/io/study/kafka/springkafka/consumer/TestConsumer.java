package io.study.kafka.springkafka.consumer;

import static io.study.kafka.springkafka.utils.KafkaTopicConstants.TEST_TOPIC;

import io.study.kafka.domain.message.TestMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestConsumer {

    private static final String TEST_REAL_TIME_CONSUMER_GROUP = "test-real-time-consumer-group";

    @KafkaListener(
        topics = {TEST_TOPIC},
        groupId = TEST_REAL_TIME_CONSUMER_GROUP,
        containerFactory = "kafkaListenerFactory"
    )
    public void acceptRealTime(ConsumerRecord<String, TestMessage> record) {
        log.info("Consumed -> [key: {}, partition: {}, offset: {}][{}]",
            record.key(),
            record.partition(),
            record.offset(),
            record.value()
        );
    }

}

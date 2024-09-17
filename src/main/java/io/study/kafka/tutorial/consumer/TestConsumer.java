package io.study.kafka.tutorial.consumer;

import io.study.kafka.tutorial.model.TestMessage;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestConsumer implements Consumer<Message<TestMessage>> {

    @Override
    public void accept(Message<TestMessage> message) {
        log.info("Received message: {}", message.getPayload());
    }

}

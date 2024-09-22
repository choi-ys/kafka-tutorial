package io.study.springcloudstream.product.producer;

import io.study.springcloudstream.product.model.TestMessage;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Slf4j
@Component
public class TestProducer implements Supplier<Flux<Message<TestMessage>>> {

    private final Sinks.Many<Message<TestMessage>> sinks = Sinks.many().unicast().onBackpressureBuffer();

    public void send(TestMessage testMessage) {
        Message<TestMessage> message = MessageBuilder
            .withPayload(testMessage)
            .setHeader(KafkaHeaders.KEY, String.valueOf(testMessage.id()))
            .build();
        sinks.emitNext(message, Sinks.EmitFailureHandler.FAIL_FAST);
        log.info("Sent message: {}", message);
    }

    @Override
    public Flux<Message<TestMessage>> get() {
        return sinks.asFlux();
    }

}

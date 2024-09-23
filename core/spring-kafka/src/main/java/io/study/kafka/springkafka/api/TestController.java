package io.study.kafka.springkafka.api;

import io.study.kafka.domain.message.TestMessage;
import io.study.kafka.springkafka.producer.TestProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestProducer testProducer;

    @PostMapping("/message")
    void publish(@RequestBody TestMessage testMessage) {
        testProducer.send(testMessage);
    }

}

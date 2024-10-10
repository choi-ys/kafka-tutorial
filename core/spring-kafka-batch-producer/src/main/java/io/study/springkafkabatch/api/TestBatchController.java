package io.study.springkafkabatch.api;

import io.study.kafka.domain.message.TestMessage;
import io.study.springkafkabatch.producer.TestBatchProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestBatchController {

    private final TestBatchProducer testBatchProducer;

    @PostMapping("/message/bulk")
    void publishBulk(@RequestBody TestMessage testMessage) {
        testBatchProducer.sendMessages(testMessage);
    }

}

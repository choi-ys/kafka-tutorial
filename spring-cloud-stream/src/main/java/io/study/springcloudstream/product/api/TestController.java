package io.study.springcloudstream.product.api;

import io.study.springcloudstream.product.model.TestMessage;
import io.study.springcloudstream.product.producer.TestProducer;
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

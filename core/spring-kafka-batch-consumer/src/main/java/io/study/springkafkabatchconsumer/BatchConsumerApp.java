package io.study.springkafkabatchconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatchConsumerApp {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BatchConsumerApp.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

}

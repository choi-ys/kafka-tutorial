package io.study.kafka.springkafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class SpringKafkaApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaApp.class, args);
    }

}

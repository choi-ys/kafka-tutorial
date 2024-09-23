package io.study.kafka.springcloudstream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "io.study.kafka")
public class SpringCloudStreamApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamApp.class, args);
    }

}

package io.study.springcloudstream.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "Spring Cloud Stream Kafka Example API",
        description = "For messaging publish and consume"
    )
)
@Configuration
public class SwaggerConfig {

}

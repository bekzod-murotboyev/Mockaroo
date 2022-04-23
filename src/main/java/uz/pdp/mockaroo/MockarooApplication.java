package uz.pdp.mockaroo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition
@SpringBootApplication
@EnableConfigurationProperties
@EnableScheduling
public class MockarooApplication {
    public static void main(String[] args) {
        SpringApplication.run(MockarooApplication.class, args);
    }

}

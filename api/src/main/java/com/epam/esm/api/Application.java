package com.epam.esm.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.epam.esm", exclude = { SecurityAutoConfiguration.class })  //TODO remove exclude
@EntityScan(basePackages = "com.epam.esm.core.entity")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

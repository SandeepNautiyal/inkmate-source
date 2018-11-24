package com.inkmate.app.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages = "com.inkmate.app")
@EntityScan("com.inkmate.app.data")
@EnableJpaRepositories(basePackages = "com.inkmate.app.dao")
public class InkmateApplication {
    public static void main(String[] args) {
        SpringApplication.run(InkmateApplication.class, args);
    }
}

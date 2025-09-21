package com.example.gasprombankjavabusiness;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class JavaBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaBusinessApplication.class, args);
    }

}

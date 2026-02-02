package com.microservices.crops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CropsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CropsApplication.class, args);
    }

}

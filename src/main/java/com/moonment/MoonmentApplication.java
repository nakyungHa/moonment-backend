package com.moonment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableScheduling
public class MoonmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoonmentApplication.class, args);
    }

}

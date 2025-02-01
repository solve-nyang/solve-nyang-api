package com.ssafy.solvedpick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class SolvedPickApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolvedPickApplication.class, args);
    }

}

package com.card.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CardChallengeBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardChallengeBackApplication.class, args);
    }

}

package com.example.usersubscription;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UserSubscriptionApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserSubscriptionApplication.class, args);
    }
}

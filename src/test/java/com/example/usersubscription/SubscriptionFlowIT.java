package com.example.usersubscription;

import com.example.usersubscription.dto.SubscriptionDTO;
import com.example.usersubscription.dto.UserDTO;
import com.example.usersubscription.dto.request.CreateUserRequest;
import com.example.usersubscription.dto.request.SubscriptionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SubscriptionFlowIT {

    @Container
    static PostgreSQLContainer<?> db =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("usersub");

    @DynamicPropertySource
    static void props(DynamicPropertyRegistry r) {
        r.add("spring.datasource.url", db::getJdbcUrl);
        r.add("spring.datasource.username", db::getUsername);
        r.add("spring.datasource.password", db::getPassword);
    }

    @Autowired
    TestRestTemplate rest;

    @Test
    void fullFlow() {
        // before
        Long userId = rest.postForEntity("/users",
                        new CreateUserRequest("Tim", "t@test.com"), UserDTO.class)
                .getBody().id();

        rest.postForEntity("/users/{id}/subscriptions",
                new SubscriptionRequest("Netflix"), Void.class, userId);

        SubscriptionDTO[] subs =
                rest.getForObject("/users/{id}/subscriptions", SubscriptionDTO[].class, userId);
        assertEquals(1, subs.length);

        rest.delete("/users/{id}/subscriptions/{subId}", userId, subs[0].id());
        // when
        SubscriptionDTO[] after =
                rest.getForObject("/users/{id}/subscriptions", SubscriptionDTO[].class, userId);
        // then
        assertEquals(0, after.length);
    }

    @Test
    void validationErrorRequest() {
        HttpStatusCode statusCode = rest.postForEntity("/users/{id}/subscriptions", new SubscriptionRequest(null), Void.class, 5L)
                .getStatusCode();
        assertThat(statusCode).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}

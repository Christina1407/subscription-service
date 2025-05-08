package com.example.usersubscription;

import com.example.usersubscription.dto.TopSubscriptionDTO;
import com.example.usersubscription.dto.UserDTO;
import com.example.usersubscription.dto.request.CreateUserRequest;
import com.example.usersubscription.dto.request.SubscriptionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SubscriptionStatsIT {

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
    void topSubscriptions_sortedByCount() {
        // before
        Long user1 = rest.postForEntity("/users",
                        new CreateUserRequest("Ann", "a@test.com"), UserDTO.class)
                .getBody().id();

        Long user2 = rest.postForEntity("/users",
                        new CreateUserRequest("Bob", "b@test.com"), UserDTO.class)
                .getBody().id();

        rest.postForEntity("/users/{id}/subscriptions",
                new SubscriptionRequest("Netflix"), Void.class, user1);
        rest.postForEntity("/users/{id}/subscriptions",
                new SubscriptionRequest("Netflix"), Void.class, user2);
        rest.postForEntity("/users/{id}/subscriptions",
                new SubscriptionRequest("YouTube"), Void.class, user1);

        // when
        TopSubscriptionDTO[] top =
                rest.getForObject("/subscriptions/top", TopSubscriptionDTO[].class);

        // then
        assertTrue(top.length >= 2);

        Map<String, Long> map = Map.of(
                top[0].serviceName(), top[0].count(),
                top[1].serviceName(), top[1].count());

        assertEquals(2L, map.get("Netflix"));
        assertEquals(1L, map.get("YouTube"));
        assertTrue(top[0].count() >= top[1].count());
    }
}

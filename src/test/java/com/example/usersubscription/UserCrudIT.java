package com.example.usersubscription;

import com.example.usersubscription.dto.UserDTO;
import com.example.usersubscription.dto.request.CreateUserRequest;
import com.example.usersubscription.dto.request.UpdateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserCrudIT {

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
    void crud() {
        // create
        ResponseEntity<UserDTO> created = rest.postForEntity("/users",
                new CreateUserRequest("Kate", "k@test.com"), UserDTO.class);
        assertEquals(201, created.getStatusCodeValue());
        Long id = created.getBody().id();

        // read
        UserDTO dto = rest.getForObject("/users/{id}", UserDTO.class, id);
        assertEquals("Kate", dto.name());

        // update
        rest.put("/users/{id}", new UpdateUserRequest("Kate 2", "k@test.com"), id);
        UserDTO updated = rest.getForObject("/users/{id}", UserDTO.class, id);
        assertEquals("Kate 2", updated.name());

        // delete (soft)
        rest.delete("/users/{id}", id);
        ResponseEntity<String> after = rest.getForEntity("/users/{id}", String.class, id);
        assertEquals(404, after.getStatusCodeValue());
    }
}

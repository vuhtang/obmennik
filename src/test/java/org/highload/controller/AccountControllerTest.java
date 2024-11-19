package org.highload.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class AccountControllerTest {
    @LocalServerPort
    private Integer port;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    AccountController accountController;
    @BeforeAll
    public static void setUp() {
        postgresContainer.start();
    }

    @AfterAll
    public static void tearDown() {
        postgresContainer.stop();
    }


    @Test
    public void testGetAllAccountsIntegration() {
        RestAssured.baseURI = "http://localhost:" + port;

        // Выполнение запроса и проверка результата
        RestAssured.get("/accounts/").then()
                .statusCode(200)
                .body("size()",  hasSize(0));
    }
}
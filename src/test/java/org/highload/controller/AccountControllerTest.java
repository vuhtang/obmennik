package org.highload.controller;

import io.restassured.RestAssured;
import org.highload.service.AccountService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class AccountControllerTest {
    @Autowired
    private AccountService accountService;
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
    public void testGetAllAccountsWithoutDataIntegration() {
        RestAssured.baseURI = "http://localhost:" + port;

        RestAssured.get("/accounts/").then()
                .statusCode(204);
    }
    @Test
    public void testGetAllAccountsIntegration() {
        RestAssured.baseURI = "http://localhost:" + port;
        accountService.createTestData();

        RestAssured.get("/accounts/").then()
                .statusCode(200)
                .contentType("application/json")
                .body("size()",  equalTo(1));
    }


}
package org.highload.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.highload.service.AccountService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.hamcrest.Matchers.greaterThan;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class AccountControllerTest {

    @Autowired
    private AccountService accountService;
    @LocalServerPort
    private Integer port;


    //TODO postgres version
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

    @BeforeEach
    public void initializeTestData() {
        accountService.createTestData();
    }

    @Test
    public void testGetAllAccountsIntegration() {
        RestAssured.baseURI = "http://localhost:" + port;

        RestAssured.get("/accounts/").then()
                .statusCode(200)
                .contentType("application/json")
                .body("size()", equalTo(2));
    }

    @Test
    public void testGetAccountById() {
        RestAssured.baseURI = "http://localhost:" + port;

        Integer firstAccountId = RestAssured.get("/accounts/").then().extract().body().jsonPath().get("[0].id");

        RestAssured.get("/accounts/{id}", firstAccountId).then()
                .statusCode(200)
                .body("id", equalTo(firstAccountId));
    }


    @Test
    public void testAddAccountWallet(){
        RestAssured.baseURI = "http://localhost:" + port;

        String privateKey = generateMockPrivateKey();

        given()
                .contentType(ContentType.TEXT)
                .header("Content-Type", "text/plain")
                .body(privateKey)
                .when()
                .post("/accounts/{id}/wallets", 1)
                .then()
                .statusCode(201);

        RestAssured.get("/accounts/{id}/wallets", 1).then()
                .statusCode(200)
                .body("size()", equalTo(1));
    }

    private String generateMockPrivateKey() {

        return "0x123456789abcdef123456789abcdef123456789abcdeF";
    }
}
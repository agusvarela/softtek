package com.softtek.interview.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class PriceControllerIntegrationTest {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void whenRequestPriceEndpointOnDay14At10AM_thenSuccessfulResponse() {
        testSuccessfulResponse("/priority-price?applicationDate=2020-06-14T10:00&productId=35455&brandId=1");
    }

    @Test
    public void whenRequestPriceEndpointOnDay14At4PM_thenSuccessfulResponse() {
        testSuccessfulResponse("/priority-price?applicationDate=2020-06-14T16:00&productId=35455&brandId=1");
    }

    @Test
    public void whenRequestPriceEndpointOnDay14At9PM_thenSuccessfulResponse() {
        testSuccessfulResponse("/priority-price?applicationDate=2020-06-14T21:00&productId=35455&brandId=1");
    }

    @Test
    public void whenRequestPriceEndpointOnDay15At10AM_thenSuccessfulResponse() {
        testSuccessfulResponse("/priority-price?applicationDate=2020-06-15T10:00&productId=35455&brandId=1");
    }

    @Test
    public void whenRequestPriceEndpointOnDay16At9PM_thenSuccessfulResponse() {
        testSuccessfulResponse("/priority-price?applicationDate=2020-06-16T21:00&productId=35455&brandId=1");
    }

    @Test
    public void whenRequestPriceEndpoint_thenNotFoundResponse() {
        testNotFoundResponse("/priority-price?applicationDate=2020-06-16T21:00&productId=1&brandId=1");
    }

    @Test
    public void whenRequestPriceEndpointWithMalformedDate_thenBadRequestResponse() {
        testBadRequest("/priority-price?applicationDate=2020-06-16T21:0&productId=1&brandId=1");
    }

    private void testSuccessfulResponse(String url) {
        given()
                .when()
                .get(url)
                .then()
                .statusCode(200)
                .assertThat()
                .body("productId", notNullValue())
                .body("brandId", notNullValue())
                .body("price", isA(Number.class))
                .body("currency", equalTo("EUR"));
    }

    private void testNotFoundResponse(String url) {
        given()
                .when()
                .get(url)
                .then()
                .statusCode(404);
    }

    private void testBadRequest(String url) {
        given()
                .when()
                .get(url)
                .then()
                .statusCode(400)
                .assertThat()
                .body("errorType", notNullValue())
                .body("errorDescription", notNullValue())
                .body("errorMessage", notNullValue());
    }
}

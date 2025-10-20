package ru.netology.rest;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

class MobileBankApiTest {
    @Test
    void shouldReturnAccounts() {
        // Given - When - Then
        // Предусловия
        given()
                .baseUri("http://localhost:9999/api/v1")
        // Выполняемые действия
        .when()
                .get("/demo/accounts")
        // Проверки
        .then()
                .statusCode(200)
                // Проверка соответствия JSON Schema
                .body(matchesJsonSchemaInClasspath("accounts.schema.json"));
    }

    @Test
    void shouldReturnSpecificAccount() {
        given()
                .baseUri("http://localhost:9999/api/v1")
        .when()
                .get("/demo/accounts")
        .then()
                .statusCode(200)
                // Проверяем, что возвращается массив
                .body("", hasSize(greaterThan(0)))
                // Проверяем наличие обязательных полей в первом элементе
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue())
                .body("[0].number", notNullValue())
                .body("[0].balance", notNullValue())
                .body("[0].currency", notNullValue())
                // Проверяем, что currency имеет одно из допустимых значений
                .body("[0].currency", anyOf(equalTo("RUB"), equalTo("USD")));
    }
}
package com.homeBankingTest.AutomatedTests.sprint2;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class Sprint2 {

    private final String accountUrl = "http://localhost:8084/account";
    private final String userUrl = "http://localhost:8084/user";

    String userId = "/1";
    private final String bearerToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ2R2lFVzl1RDBqZDR2cTlQVjloR1o2R09Rd3Q2UjlLRzdLNWVha2t2ZzhnIn0.eyJleHAiOjE3MTU3NzgzNDUsImlhdCI6MTcxNTc3Nzc0NSwianRpIjoiNjMyZGVkM2UtMThmOS00ZmNlLWI5YTMtZTRlN2I1M2Y1ZGE5IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9kaC1tb25leS11c2VycyIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmMmU5ZTc5Ni0zMzdiLTRhYWUtOThmYy0xODk0OWQ5ODY4MDMiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJhcGktZGgtbW9uZXkiLCJzZXNzaW9uX3N0YXRlIjoiY2M3ZjYzYTUtZjBhYy00MzM2LWJkMWItMDBkM2M0OWNlNmQyIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjkwOTAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJkZWZhdWx0LXJvbGVzLWRoLW1vbmV5LXVzZXJzIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwic2lkIjoiY2M3ZjYzYTUtZjBhYy00MzM2LWJkMWItMDBkM2M0OWNlNmQyIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5hbWUiOiJQYWJsbyBPYnJlZ29uIiwicHJlZmVycmVkX3VzZXJuYW1lIjoicGFibG9ubTAzIiwiZ2l2ZW5fbmFtZSI6IlBhYmxvIiwiZmFtaWx5X25hbWUiOiJPYnJlZ29uIiwiZW1haWwiOiJwYWJsb25pY29sYXNtQGdtYWlsLmNvbSJ9.p5zKYp7gMc1Iwk7cBBotdWATPIFt7AK7g7qdS_gooY6XtNJ84qt2hbWrjyJXUsWOymswWFnfPEJWXSLcdSiQAvp-S2qNtiXfoT6SizHFayTKxVcFmwZQxuzXmWPw_adx18iJ2O2o71oAAZCXQ3WxMyC8SIeanw512dvtTQgDdc22rijlYhP0quhnFGNAC8tBb_rt_bsSv1DvyY-xDSz4VLzhgBNQDP123-x3GpZoghBBoLGCqvMJ-Je1Y8IdDOiVSQIcXj99MTzeuQhggnXVKbp8gu4AhoyQM_eMDP7iCXVRsUK6bevKU0IYuAkAiZ9kxxVY6l-vdO47HkuhB5Y7Uw";

    // OBTENER INFORMACION DE LA CUENTA
    @Test
    public void getAccountInformation() {

        given()
                .header("Authorization", "Bearer " + bearerToken)
                .get(accountUrl + userId)
                .then()
                .statusCode(200)
                .log().body();
    }

    // ULTIMOS MOVIMIENTOS DE LA CUENTA
    @Test
    public void getLastFiveTrasnsactions() {

        String transactionPath = "/transactions";

        given()
                .header("Authorization", "Bearer " + bearerToken)
                .get(accountUrl + userId + transactionPath)
                .then()
                .statusCode(200)
                .log().body();
    }

    //ACTUALIZAR USUARIO
    @Test
    public void updateUser() {


        String updateUserPath = "/update-user";

        JsonObject request = new JsonObject();
        request.addProperty("name", "Mauro");
        request.addProperty("lastName", "Tapia");
        request.addProperty("username", "maurotapia12");
        request.addProperty("email", "mauro12@gmail.com");
        request.addProperty("phoneNumber", "1532676801");
        request.addProperty("password", "mauro123");

        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .body(request)
                .patch(userUrl + updateUserPath)
                .then()
                .statusCode(200)
                .log().body();
    }

    //ACTUALIZAR ALIAS
    @Test
    public void updateAlias() {

        String updateAliasPath = "/update-alias";

        JsonObject request = new JsonObject();
        request.addProperty("alias", "nuevo.alias");

        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .body(request)
                .patch(userUrl + updateAliasPath)
                .then()
                .statusCode(200)
                .log().body();
    }

    //ACTUALIZAR PASSWORD
    @Test
    public void updatePassword() {

        String updatePasswordPath = "/update-password";

        JsonObject request = new JsonObject();
        request.addProperty("password", "123abc");
        request.addProperty("passwordRepeated", "123abc");

        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .body(request)
                .patch(userUrl + updatePasswordPath)
                .then()
                .statusCode(200)
                .log().body();
    }

    //REGISTRAR TARJETA
    @Test
    public void registerCard() {

        String registerCardPath = "/register-card";

        JsonObject request = new JsonObject();
        request.addProperty("holder", "Mauro Tapia");
        request.addProperty("number", "1234123412341234");
        request.addProperty("expirationDate", "2024-12-13");
        request.addProperty("cvv", "666");

        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .body(request)
                .post(accountUrl + registerCardPath)
                .then()
                .statusCode(201)
                .log().body();
    }

    //REGISTRAR TARJETA QUE YA EXISTE
    @Test
    public void registerCardFail_1() {

        String registerCardPath = "/register-card";

        JsonObject request = new JsonObject();
        request.addProperty("holder", "Tomas Tapia");
        request.addProperty("number", "1234123412341234");
        request.addProperty("expirationDate", "2027-05-20");
        request.addProperty("cvv", "666");

        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .body(request)
                .post(accountUrl + registerCardPath)
                .then()
                .statusCode(409)
                .log().body();
    }

    //REGISTRAR TARJETA CON FORMULARIO INCOMPLETO
    @Test
    public void registerCardFail_2() {

        String registerCardPath = "/register-card";

        JsonObject request = new JsonObject();
        request.addProperty("holder", "Luca Tapia");
        request.addProperty("number", "1234123412341237");
        request.addProperty("expirationDate", "2027-05-20");


        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .body(request)
                .post(accountUrl + registerCardPath)
                .then()
                .statusCode(400)
                .log().body();
    }

    //OBTENER TARJETA POR ID
    @Test
    public void getCard() {

        String cardIdPath="/1";
        String cardPath = "/card";

        given()
                .header("Authorization", "Bearer " + bearerToken)
                .get(accountUrl + cardPath + cardIdPath)
                .then()
                .statusCode(200)
                .log().body();
    }

    //OBTENER TARJETA CON ID INEXISTENTE
    @Test
    public void getCardFail() {

        String cardIdPath="/2";
        String cardPath = "/card";

        given()
                .header("Authorization", "Bearer " + bearerToken)
                .get(accountUrl + cardPath + cardIdPath)
                .then()
                .statusCode(404)
                .log().body();
    }

    //OBTENER TODAS LAS TARJETAS DE UNA MISMA CUENTA
    @Test
    public void getAllCardsById_1() {

        String cardsPath = "/cards";

        given()
                .header("Authorization", "Bearer " + bearerToken)
                .get(accountUrl + cardsPath)
                .then()
                .statusCode(200)
                .log().body();
    }

    //OBTENER TODAS LAS TARJETAS DE UNA MISMA CUENTA PERO NINGUNA REGISTRADA
    @Test
    public void getAllCardsById_2() {

        String cardsPath = "/cards";

        given()
                .header("Authorization", "Bearer " + bearerToken)
                .get(accountUrl + cardsPath)
                .then()
                .statusCode(200)
                .log().body();
    }

    //BORRAR TARJETA POR ID
    @Test
    public void deleteCard() {

        String deleteCardPath = "/delete-card";
        String cardId = "/2";

        given()
                .header("Authorization", "Bearer " + bearerToken)
                .delete(accountUrl + deleteCardPath + cardId)
                .then()
                .statusCode(200)
                .log().body();
    }

    //BORRAR TARJETA POR ID QUE NO EXISTE
    @Test
    public void deleteCardFail() {

        String deleteCardPath = "/delete-card";
        String cardId = "/3";

        given()
                .header("Authorization", "Bearer " + bearerToken)
                .delete(accountUrl + deleteCardPath + cardId)
                .then()
                .statusCode(404)
                .log().body();
    }

}


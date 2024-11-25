package com.homeBankingTest.AutomatedTests.sprint3;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class Sprint3 {

    private final String accountUrl = "http://localhost:8084/account";
    private final String bearerToken ="eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ2R2lFVzl1RDBqZDR2cTlQVjloR1o2R09Rd3Q2UjlLRzdLNWVha2t2ZzhnIn0.eyJleHAiOjE3MTU3NzgzNDUsImlhdCI6MTcxNTc3Nzc0NSwianRpIjoiNjMyZGVkM2UtMThmOS00ZmNlLWI5YTMtZTRlN2I1M2Y1ZGE5IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9kaC1tb25leS11c2VycyIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmMmU5ZTc5Ni0zMzdiLTRhYWUtOThmYy0xODk0OWQ5ODY4MDMiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJhcGktZGgtbW9uZXkiLCJzZXNzaW9uX3N0YXRlIjoiY2M3ZjYzYTUtZjBhYy00MzM2LWJkMWItMDBkM2M0OWNlNmQyIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjkwOTAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJkZWZhdWx0LXJvbGVzLWRoLW1vbmV5LXVzZXJzIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwic2lkIjoiY2M3ZjYzYTUtZjBhYy00MzM2LWJkMWItMDBkM2M0OWNlNmQyIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5hbWUiOiJQYWJsbyBPYnJlZ29uIiwicHJlZmVycmVkX3VzZXJuYW1lIjoicGFibG9ubTAzIiwiZ2l2ZW5fbmFtZSI6IlBhYmxvIiwiZmFtaWx5X25hbWUiOiJPYnJlZ29uIiwiZW1haWwiOiJwYWJsb25pY29sYXNtQGdtYWlsLmNvbSJ9.p5zKYp7gMc1Iwk7cBBotdWATPIFt7AK7g7qdS_gooY6XtNJ84qt2hbWrjyJXUsWOymswWFnfPEJWXSLcdSiQAvp-S2qNtiXfoT6SizHFayTKxVcFmwZQxuzXmWPw_adx18iJ2O2o71oAAZCXQ3WxMyC8SIeanw512dvtTQgDdc22rijlYhP0quhnFGNAC8tBb_rt_bsSv1DvyY-xDSz4VLzhgBNQDP123-x3GpZoghBBoLGCqvMJ-Je1Y8IdDOiVSQIcXj99MTzeuQhggnXVKbp8gu4AhoyQM_eMDP7iCXVRsUK6bevKU0IYuAkAiZ9kxxVY6l-vdO47HkuhB5Y7Uw";

    //OBTENER TODAS LAS TRANSFERENCIAS
    @Test
    public void getAllActivity() {

        String activityPath = "/activity";

        given()
                .header("Authorization", "Bearer " + bearerToken)
                .get(accountUrl + activityPath)
                .then()
                .statusCode(200)
                .log().body();
    }

    //OBTENER TODAS LAS TRANSFERENCIAS SIN TENER ALGUNA HECHA
    @Test
    public void getAllActivityFail_1() {

        String activityPath = "/activity";

        given()
                .header("Authorization", "Bearer " + bearerToken)
                .get(accountUrl + activityPath)
                .then()
                .statusCode(404)
                .log().body();
    }

    //OBTENER TRANSFERENCIA POR ID
    @Test
    public void getSingleActivity() {

        String singleActivityPath = "/activity";
        String transactionId = "/1";

        given()
                .header("Authorization", "Bearer " + bearerToken)
                .get(accountUrl + singleActivityPath + transactionId)
                .then()
                .statusCode(200)
                .log().body();
    }

    //OBTENER TRANSFERENCIA CON ID INEXISTENTE
    @Test
    public void getSingleActivityFail_1() {

        String singleActivityPath = "/activity";
        String transactionId = "/2";

        given()
                .header("Authorization", "Bearer " + bearerToken)
                .get(accountUrl + singleActivityPath + transactionId)
                .then()
                .statusCode(404)
                .log().body();
    }

    //OBTENER TRANSFERENCIA POR ID SIN ESTAR LOGUEADO
    @Test
    public void getSingleActivityFail_2() {

        String singleActivityPath = "/activity";
        String transactionId = "/1";

        given()
                .get(accountUrl + singleActivityPath + transactionId)
                .then()
                .statusCode(401)
                .log().body();
    }

    //AGREGAR DINERO DESDE UNA TARJETA A LA CUENTA
    @Test
    public void addMoney() {

        String depositPath = "/deposit";

        JsonObject request = new JsonObject();
        request.addProperty("cardId", 3);
        request.addProperty("amount", 2000.0);

        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .body(request)
                .patch(accountUrl + depositPath)
                .then()
                .statusCode(200)
                .log().body();
    }

        //AGREGAR DINERO DESDE UNA TARJETA NO REGISTRADA
    @Test
    public void addMoneyFail() {

        String depositPath = "/deposit";

        JsonObject request = new JsonObject();
        request.addProperty("cardId", 2);
        request.addProperty("amount", 2000.0);

        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .body(request)
                .patch(accountUrl+ depositPath)
                .then()
                .statusCode(404)
                .log().body();
    }
}

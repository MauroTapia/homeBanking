package com.homeBankingTest.AutomatedTests.sprint4;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class Sprint4 {

    private final String accountUrl = "http://localhost:8084/account";
    private final String bearerToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ2R2lFVzl1RDBqZDR2cTlQVjloR1o2R09Rd3Q2UjlLRzdLNWVha2t2ZzhnIn0.eyJleHAiOjE3MTU3NzkwMzcsImlhdCI6MTcxNTc3ODQzNywianRpIjoiMzEwNmNjMTUtNmU2Zi00OWU4LTk4OGUtNzQwMTAxYWI0YWZmIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9kaC1tb25leS11c2VycyIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmMmU5ZTc5Ni0zMzdiLTRhYWUtOThmYy0xODk0OWQ5ODY4MDMiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJhcGktZGgtbW9uZXkiLCJzZXNzaW9uX3N0YXRlIjoiYzM3YzcyYzUtOGU4MS00MGU3LWExYzUtMjgxZDVlOTA5NzBlIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjkwOTAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJkZWZhdWx0LXJvbGVzLWRoLW1vbmV5LXVzZXJzIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwic2lkIjoiYzM3YzcyYzUtOGU4MS00MGU3LWExYzUtMjgxZDVlOTA5NzBlIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5hbWUiOiJQYWJsbyBPYnJlZ29uIiwicHJlZmVycmVkX3VzZXJuYW1lIjoicGFibG9ubTAzIiwiZ2l2ZW5fbmFtZSI6IlBhYmxvIiwiZmFtaWx5X25hbWUiOiJPYnJlZ29uIiwiZW1haWwiOiJwYWJsb25pY29sYXNtQGdtYWlsLmNvbSJ9.ZUBjb8c8ZzolAVV2qNIqRe5RQyaNCxPNx5zL68J8ZIq9OVGwRlIMg5AVB9yinw2vlVBBD3H1ebutyya0tTjlulwT6cXfgGsjVbaVQyiNq0MbcJ7jdeN3HUZjBJ3NdzcEbvycUdxncYkcx2KcJTNnchv_B_GvbXKniCIZZyaI6VPNQ6pfUuPu-oxKy5fXsNu-j6OgWy250w0jWRphdF-QU8L6IpL1eZ_WvwIJRJxpbkD040E5PZD6KOSsRlvX_YGQl0O_eto3QPw95XOdXNck88nL0N_2vpYpvba1cbxmEpdPgfKkY1DrlnR05--v4GfO0LBQG0DgyjS31o49SjzV0w";

    //ENVIAR DINERO CON ALIAS
    @Test
    public void addMoney() {

        String sendMoneyPath = "/send-money";

        JsonObject request = new JsonObject();
        request.addProperty("destinyAccount", "TACOS.ZONAS.ERROR");
        request.addProperty("amount", 1000.0);

        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .body(request)
                .post(accountUrl + sendMoneyPath)
                .then()
                .statusCode(200)
                .log().body();
    }

    //ENVIAR DINERO CON CVU
    @Test
    public void addMoney_2() {

        String sendMoneyPath = "/send-money";

        JsonObject request = new JsonObject();
        request.addProperty("destinyAccount", "7031990973215436706303");
        request.addProperty("amount", 500.0);

        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .body(request)
                .post(accountUrl + sendMoneyPath)
                .then()
                .statusCode(200)
                .log().body();
    }

    //ENVIAR DINERO FALLIDO - FALTA DE DINERO
    @Test
    public void addMoneyFail() {

        String sendMoneyPath = "/send-money";

        JsonObject request = new JsonObject();
        request.addProperty("destinyAccount", "7031990973215436706303");
        request.addProperty("amount", 25000.0);

        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .body(request)
                .post(accountUrl + sendMoneyPath)
                .then()
                .statusCode(400)
                .log().body();
    }

    //ENVIAR DINERO FALLIDO - CVU INEXISTENTE
    @Test
    public void addMoneyFail_2() {

        String sendMoneyPath = "/send-money";

        JsonObject request = new JsonObject();
        request.addProperty("destinyAccount", "7031990973215436706302");
        request.addProperty("amount", 500.0);

        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .body(request)
                .post(accountUrl + sendMoneyPath)
                .then()
                .statusCode(404)
                .log().body();
    }
}

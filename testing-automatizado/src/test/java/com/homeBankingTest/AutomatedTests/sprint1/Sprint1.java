package com.homeBankingTest.AutomatedTests.sprint1;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class Sprint1 {

    private final String url = "http://localhost:8084/user";
    private final String registerPath = "/register";
    private final String loginPath = "/login";
    private final String bearerToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ2R2lFVzl1RDBqZDR2cTlQVjloR1o2R09Rd3Q2UjlLRzdLNWVha2t2ZzhnIn0.eyJleHAiOjE3MTU3NzY2MTMsImlhdCI6MTcxNTc3NjAxMywianRpIjoiZDNjYmM2ZDQtMzkwNC00NTRhLWE2MGQtYTkzOGRlNDg5OWNmIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9kaC1tb25leS11c2VycyIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmMmU5ZTc5Ni0zMzdiLTRhYWUtOThmYy0xODk0OWQ5ODY4MDMiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJhcGktZGgtbW9uZXkiLCJzZXNzaW9uX3N0YXRlIjoiZDg5NDFlODMtMDljNC00NWVhLTlmYmQtNjZlMmQ1YjJkZDIyIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjkwOTAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJkZWZhdWx0LXJvbGVzLWRoLW1vbmV5LXVzZXJzIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwic2lkIjoiZDg5NDFlODMtMDljNC00NWVhLTlmYmQtNjZlMmQ1YjJkZDIyIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5hbWUiOiJQYWJsbyBNZXJpbm8iLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJwYWJsb25tMDMiLCJnaXZlbl9uYW1lIjoiUGFibG8iLCJmYW1pbHlfbmFtZSI6Ik1lcmlubyIsImVtYWlsIjoicGFibG9uaWNvbGFzbUBob3RtYWlsLmNvbSJ9.LtudhVQZ490B3DpT5XZ6_ChL4V3DFxQNTiZ4rxJP7dWOXDiZ1e1RxLCwi8iGsqSSxjiTslJrwsIvMOZ4NqXbiCcfsWPHV75vLDYA3_THN_bz9-7AmhJrZuVFV-tEzYLA71ZboASaqvBMsrR11gDHH9sEBznLFtBcDJLpzQVbj5FO_ADkrv9mvYtmRWZRbgQQDl4Px3FfKKs5hof4XH2K47FN4Z9cFW-KdgYXDENmY-uO2bYWD-JQ3je3N5FuAFYHJGw1eCkFj3vMdwJfMuB0rJI5Ly6cEuSCGgRMdq2-_HnZ_O9H0p5MpgJ0MLp4b9Y6-jBTlZf4vyZih9POYxA5EQ";

    // REGISTRAR EXITOSO DE USUARIO
    @Test
    public void register() {

        JsonObject request = new JsonObject();
        request.addProperty("name", "Mauro");
        request.addProperty("lastName", "Tapia");
        request.addProperty("username", "maurotapia12");
        request.addProperty("email", "mauro12@gmail.com");
        request.addProperty("phoneNumber", "1532676801");
        request.addProperty("password", "mauro123");

        given()
                .contentType("application/json")
                .body(request)
                .post(url + registerPath)
                .then()
                .statusCode(201)
                .log().body();
    }

    //REGISTRO CON CAMPO FALTANTE (LASTNAME)
    @Test
    public void badRegister_1() {

        JsonObject request = new JsonObject();
        request.addProperty("name", "Mauro");
        request.addProperty("username", "maurotapia12");
        request.addProperty("email", "mauro12@gmail.com");
        request.addProperty("phoneNumber", "1532676801");
        request.addProperty("password", "mauro123");
        given()
                .contentType("application/json")
                .body(request)
                .post(url + registerPath)
                .then()
                .statusCode(500)
                .log().body();
    }

    //REGISTRAR USUARIO PREVIAMENTE REGISTRADO
    @Test
    public void badRegister_2() {

        JsonObject request = new JsonObject();
        request.addProperty("name", "Mauro");
        request.addProperty("lastName", "Tapia");
        request.addProperty("username", "maurotapia12");
        request.addProperty("email", "mauro12@gmail.com");
        request.addProperty("phoneNumber", "1532676801");
        request.addProperty("password", "mauro123");

        given()
                .contentType("application/json")
                .body(request)
                .post(url + registerPath)
                .then()
                .statusCode(400)
                .log().body();
    }

    //REGISTRA USUARIO CON USERNAME USADO
    @Test
    public void badRegister_3() {

        JsonObject request = new JsonObject();
        request.addProperty("name", "Lara");
        request.addProperty("lastName", "Leguizamon");
        request.addProperty("username", "maurotapia12");
        request.addProperty("email", "lara@gmail.com");
        request.addProperty("phoneNumber", "1536866812");
        request.addProperty("password", "lara123");

        given()
                .contentType("application/json")
                .body(request)
                .post(url + registerPath)
                .then()
                .statusCode(400)
                .log().body();
    }

    //LOGIN EXITOSO
    @Test
    public void login() {

        JsonObject request = new JsonObject();
        request.addProperty("email", "mauro12@gmail.com");
        request.addProperty("password", "mauro123");

        given()
                .contentType("application/json")
                .body(request)
                .post(url + loginPath)
                .then()
                .statusCode(200)
                .log().body();
    }

    //LOGIN FALLIDO - CAMPO INCORRECTO
    @Test
    public void badLogin() {

        JsonObject request = new JsonObject();
        request.addProperty("email", "mauro12@gmail.com");
        request.addProperty("password", "mauro123");

        given()
                .contentType("application/json")
                .body(request)
                .post(url + loginPath)
                .then()
                .statusCode(500)
                .log().body();
    }

    //RECUPERAR CONTRASEÑA
    @Test
    public void forgotPassword() {

        String forgotPasswordPath = "/maurotapia12/forgot-password";

        given()
                .put(url + forgotPasswordPath)
                .then()
                .statusCode(200)
                .log().body();
    }

    //LOGOUT EXITOSO
    @Test
    public void logout() {

        String logoutPath = "/logout";

        given()
                .header("Authorization", "Bearer " + bearerToken)
                .post(url + logoutPath)
                .then()
                .statusCode(200)
                .log().body();
    }
}

package com.homeBanking.usersService.service;

import com.homeBanking.usersService.config.KeycloakClientConfig;
import com.homeBanking.usersService.entities.AccessKeycloak;
import com.homeBanking.usersService.entities.Login;
import com.homeBanking.usersService.entities.User;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KeycloakService {
    private static final Logger logger = LoggerFactory.getLogger(KeycloakService.class);

    @Autowired
    private KeycloakClientConfig keycloakClientConfig;

    @Value("${dh.keycloak.realm}")
    private String realm;
    @Value("${dh.keycloak.serverUrl}")
    private String serverUrl;
    @Value("${dh.keycloak.clientId}")
    private String clientId;
    @Value("${dh.keycloak.clientSecret}")
    private String clientSecret;
    @Value("${dh.keycloak.tokenEndpoint}")
    private String tokenEndpoint;

    private static final String UPDATE_PASSWORD = "UPDATE_PASSWORD";

    public RealmResource getRealm() {
        logger.debug("Fetching realm: {}", realm);
        return keycloakClientConfig.getInstance().realm(realm);
    }

    public User createUser(User userKeycloak) throws Exception {
        logger.info("Starting user creation for username: {}", userKeycloak.getUsername());

        UserRepresentation userRepresentation = new UserRepresentation();
        Map<String, List<String>> attributes = new HashMap<>();

        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(userKeycloak.getUsername());
        userRepresentation.setEmail(userKeycloak.getEmail());
        userRepresentation.setFirstName(userKeycloak.getName());
        userRepresentation.setLastName(userKeycloak.getLastName());
        userRepresentation.setEmailVerified(true);
        attributes.put("phoneNumber", Collections.singletonList(String.valueOf(userKeycloak.getPhoneNumber())));
        userRepresentation.setAttributes(attributes);

        logger.debug("User representation details: {}", userRepresentation);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(userKeycloak.getPassword());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        logger.info("Sending request to Keycloak to create user");

        Response response = getRealm().users().create(userRepresentation);
        if(response.getStatus() == 409) {
            logger.error("User already exists with username: {}", userKeycloak.getUsername());
            throw new Exception("(!) User already exists");
        }

        if (response.getStatus() >= 400) {
            logger.error("Error occurred with status: {} and response: {}", response.getStatus(), response);
            throw new BadRequestException("(!) Something happened, try again later");
        }

        List<UserRepresentation> emailsFound = getRealm().users().searchByEmail(userKeycloak.getEmail(), true);
        if(emailsFound.isEmpty()) {
            logger.warn("No emails registered for email: {}", userKeycloak.getEmail());
        } else {
            logger.debug("Emails found: {}", emailsFound);
        }

        userRepresentation.setId(CreatedResponseUtil.getCreatedId(response)); //extrae el id del location (url) y lo asigna
        logger.info("User created successfully with ID: {}", userRepresentation.getId());

        return User.toUser(userRepresentation);
    }

    public AccessKeycloak login(Login login) throws Exception {
        try{

            AccessKeycloak tokenAccess = null;
            Keycloak keycloakClient = null;
            TokenManager tokenManager = null;

            keycloakClient = Keycloak.getInstance(serverUrl,realm,login.getEmail(), login.getPassword(), clientId, clientSecret);

            tokenManager = keycloakClient.tokenManager();

            tokenAccess = AccessKeycloak.builder()
                    .accessToken(tokenManager.getAccessTokenString())
                    .expiresIn(tokenManager.getAccessToken().getExpiresIn())
                    .refreshToken(tokenManager.refreshToken().getRefreshToken())
                    .scope(tokenManager.getAccessToken().getScope())
                    .build();

            return tokenAccess;

        }  catch (Exception e) {
            throw new AuthenticationException("Invalid Credentials");
        }
    }

    public void logout(String userId) {
        getRealm().users().get(userId).logout();
    }


}

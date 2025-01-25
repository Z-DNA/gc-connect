package pl.zdna.gcconnect.users.infrastructure;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.HttpStatus;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

import pl.zdna.gcconnect.users.application.AuthorizationHttpService;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class Auth0HttpService implements AuthorizationHttpService {
    private final Auth0Data auth0Data;

    private String getAccessToken() {
        final HttpResponse<JsonNode> response =
                Unirest.post(auth0Data.tokenUrl())
                        .header("Content-Type", "application/json")
                        .body(getAccessTokenRequestBody())
                        .asJson();

        if (response.getStatus() == HttpStatus.OK) {
            return response.getBody().getObject().getString("access_token");
        } else {
            // TODO: handle connection errors
            throw new RuntimeException("Failed to get access token: " + response.getBody());
        }
    }

    private Map<String, Object> getAccessTokenRequestBody() {
        final Map<String, Object> body = new HashMap<>();
        body.put("grant_type", "client_credentials");
        body.put("client_id", auth0Data.clientId());
        body.put("client_secret", auth0Data.clientSecret());
        body.put("audience", auth0Data.apiUrl());
        return body;
    }

    @Override
    public void createNewUser(
            final String username, final String password, final String inviterUsername) {
        final String url = auth0Data.apiUrl() + "users";

        final HttpResponse<JsonNode> response =
                Unirest.post(url)
                        .header("Authorization", "Bearer " + getAccessToken())
                        .header("Content-Type", "application/json")
                        .body(createNewUserRequestBody(username, password, inviterUsername))
                        .asJson();

        handleCreateNewUserResponse(response, username);
    }

    private Map<String, Object> createNewUserRequestBody(
            final String username, final String password, final String inviterUsername) {
        final Map<String, Object> body = new HashMap<>();
        body.put("connection", auth0Data.connection());
        body.put("password", password);
        body.put("username", username);
        body.put("app_metadata", Map.of("inviter", inviterUsername));
        return body;
    }

    private void handleCreateNewUserResponse(
            final HttpResponse<JsonNode> response, final String username) {
        // TODO handle errors GCC-31
        switch (response.getStatus()) {
            case HttpStatus.CREATED -> log.info("User {} created", username);
            case HttpStatus.CONFLICT ->
                    throw new RuntimeException("%s already exists".formatted(username));
            default ->
                    throw new RuntimeException(
                            "Failed to create user: %s; Error: %s"
                                    .formatted(username, response.getBody()));
        }
    }

    @Override
    public void deleteUser(final String username) {
        // TODO implement GCC-30
    }

    @Override
    public void resetPassword(final String username) {
        // TODO implement GCC-33
    }
}

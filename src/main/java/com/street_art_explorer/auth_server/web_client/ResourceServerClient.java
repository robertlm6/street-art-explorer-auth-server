package com.street_art_explorer.auth_server.web_client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class ResourceServerClient {

    private final WebClient webClient;

    @Value("${resource.server.url}")
    private String resourceServerUrl;

    @Value("${internal.auth.token}")
    private String internalAuthToken;

    public ResourceServerClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public void createAndUpdateUserInResource(Integer authId, String username, String email) {
        Map<String, Object> body = Map.of(
                "authServerUserId", authId,
                "username", username,
                "email", email
        );

        webClient.post()
                .uri(resourceServerUrl + "/internal/users/createandupdate")
                .header("X-Internal-Token", internalAuthToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}

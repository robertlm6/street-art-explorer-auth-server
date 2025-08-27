package com.street_art_explorer.auth_server.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OAuthClientDto {

    private String clientId;
    private String clientSecret;
    private Set<String> authMethods;
    private Set<String> grantTypes;
    private Set<String> scopes;
    private Set<String> redirectUri;
    private Set<String> postLogoutRedirectUris;
}

package com.street_art_explorer.auth_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

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
}

package com.street_art_explorer.auth_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateClientDto {
    private String clientId;
    private String clientSecret;
    private Set<ClientAuthenticationMethodRequestDto> authenticationMethods;
    private Set<AuthorizationGrantTypeRequestDto> authorizationGrantTypes;
    private Set<RedirectUriRequestDto> redirectUris;
    private Set<ScopeRequestDto> scopes;
    private Boolean requireProofKey;
}

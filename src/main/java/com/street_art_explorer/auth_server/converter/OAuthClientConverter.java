package com.street_art_explorer.auth_server.converter;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import com.street_art_explorer.auth_server.dto.OAuthClientDto;
import com.street_art_explorer.auth_server.entity.ClientAuthenticationMethodCustom;
import com.street_art_explorer.auth_server.entity.ClientGrantType;
import com.street_art_explorer.auth_server.entity.ClientPostLogoutRedirectUri;
import com.street_art_explorer.auth_server.entity.ClientRedirectUri;
import com.street_art_explorer.auth_server.entity.ClientScope;
import com.street_art_explorer.auth_server.entity.OAuthClient;

@Component
public class OAuthClientConverter {

    public RegisteredClient oauthClientToRegisteredClient(OAuthClient oauthClient) {
        return RegisteredClient.withId(oauthClient.getId().toString())
                .clientId(oauthClient.getClientId())
                .clientSecret(oauthClient.getClientSecret())
                .clientAuthenticationMethods(methods ->
                        oauthClient.getAuthenticationMethods().forEach(method ->
                                methods.add(new ClientAuthenticationMethod(method.getAuthMethod()))
                        )
                )
                .authorizationGrantTypes(grants ->
                        oauthClient.getGrantTypes().forEach(grant ->
                                grants.add(new AuthorizationGrantType(grant.getGrantType()))
                        )
                )
                .scopes(scopes ->
                        oauthClient.getScopes().forEach(scope ->
                                scopes.add(scope.getScope())
                        )
                )
                .redirectUris(redirects ->
                        oauthClient.getRedirectUris().forEach(uri ->
                                redirects.add(uri.getRedirectUri())
                        )
                )
                .postLogoutRedirectUris(postLogoutRedirects ->
                        oauthClient.getClientPostLogoutRedirectUris().forEach(uri ->
                                postLogoutRedirects.add(uri.getPostLogoutRedirectUri())
                        )
                )
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(10))
                        .refreshTokenTimeToLive(Duration.ofDays(30))
                        .reuseRefreshTokens(false)
                        .build())
                .build();
    }

    public OAuthClient registeredClientToOAuthClient(RegisteredClient registeredClient) {
        OAuthClient oauthClient = new OAuthClient();
        oauthClient.setId(Integer.valueOf(registeredClient.getId()));
        oauthClient.setClientId(registeredClient.getClientId());
        oauthClient.setClientSecret(registeredClient.getClientSecret());

        oauthClient.setAuthenticationMethods(
                registeredClient.getClientAuthenticationMethods().stream()
                        .map(authMethod -> new ClientAuthenticationMethodCustom(null, authMethod.getValue()))
                        .collect(Collectors.toSet())
        );

        oauthClient.setGrantTypes(
                registeredClient.getAuthorizationGrantTypes().stream()
                        .map(grantType -> new ClientGrantType(null, grantType.getValue()))
                        .collect(Collectors.toSet())
        );

        oauthClient.setScopes(
                registeredClient.getScopes().stream()
                        .map(scope -> new ClientScope(null, scope))
                        .collect(Collectors.toSet())
        );

        oauthClient.setRedirectUris(
                registeredClient.getRedirectUris().stream()
                        .map(uri -> new ClientRedirectUri(null, uri))
                        .collect(Collectors.toSet())
        );

        oauthClient.setClientPostLogoutRedirectUris(
                registeredClient.getPostLogoutRedirectUris().stream()
                        .map(uri -> new ClientPostLogoutRedirectUri(null, uri))
                        .collect(Collectors.toSet())
        );

        return oauthClient;
    }

    public OAuthClientDto oauthClientToOauthClientDTO(OAuthClient oauthClient) {
        OAuthClientDto oauthClientDto = new OAuthClientDto();
        oauthClientDto.setClientId(oauthClient.getClientId());
        oauthClientDto.setClientSecret(null);

        Set<String> authMethods = oauthClient.getAuthenticationMethods().stream()
                .map(ClientAuthenticationMethodCustom::getAuthMethod)
                .collect(Collectors.toSet());
        Set<String> grantTypes = oauthClient.getGrantTypes().stream()
                .map(ClientGrantType::getGrantType)
                .collect(Collectors.toSet());
        Set<String> scopes = oauthClient.getScopes().stream()
                .map(ClientScope::getScope)
                .collect(Collectors.toSet());
        Set<String> redirectUris = oauthClient.getRedirectUris().stream()
                .map(ClientRedirectUri::getRedirectUri)
                .collect(Collectors.toSet());
        Set<String> postLogoutRedirectUris = oauthClient.getClientPostLogoutRedirectUris().stream()
                .map(ClientPostLogoutRedirectUri::getPostLogoutRedirectUri)
                .collect(Collectors.toSet());

        oauthClientDto.setAuthMethods(authMethods);
        oauthClientDto.setGrantTypes(grantTypes);
        oauthClientDto.setScopes(scopes);
        oauthClientDto.setRedirectUri(redirectUris);
        oauthClientDto.setPostLogoutRedirectUris(postLogoutRedirectUris);

        return oauthClientDto;
    }
}

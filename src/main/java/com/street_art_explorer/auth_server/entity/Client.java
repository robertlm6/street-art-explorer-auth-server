package com.street_art_explorer.auth_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String clientId;
    @Column(nullable = false)
    private String clientSecret;
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ClientAuthenticationMethodEntity> authenticationMethods;
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<AuthorizationGrantTypeEntity> authorizationGrantTypes;
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<RedirectUriEntity> redirectUris;
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ScopeEntity> scopes;
    private Boolean requireProofKey;

    public static RegisteredClient toRegisteredClient(Client client) {
        RegisteredClient.Builder clientBuilder = RegisteredClient.withId(client.getClientId())
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .clientIdIssuedAt(new Date().toInstant())
                .clientAuthenticationMethods(am ->
                        am.addAll(client.getAuthenticationMethods().stream()
                                .map(ClientAuthenticationMethodEntity::getMethod)
                                .map(ClientAuthenticationMethod::new)
                                .toList()
                        )
                )
                .authorizationGrantTypes(agt ->
                        agt.addAll(client.getAuthorizationGrantTypes().stream()
                                .map(AuthorizationGrantTypeEntity::getGrantType)
                                .map(AuthorizationGrantType::new)
                                .toList()
                        )
                )
                .redirectUris(ru ->
                        ru.addAll(client.getRedirectUris().stream()
                                .map(RedirectUriEntity::getUri)
                                .toList()
                        )
                )
                .scopes(scope ->
                        scope.addAll(client.getScopes().stream()
                                .map(ScopeEntity::getScope)
                                .toList()
                        )
                )
                .clientSettings(ClientSettings.builder().requireProofKey(client.getRequireProofKey()).build());
        return clientBuilder.build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id != null && id.equals(client.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

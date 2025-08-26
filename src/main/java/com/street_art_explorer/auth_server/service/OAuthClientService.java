package com.street_art_explorer.auth_server.service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.street_art_explorer.auth_server.converter.OAuthClientConverter;
import com.street_art_explorer.auth_server.dto.OAuthClientDto;
import com.street_art_explorer.auth_server.entity.ClientAuthenticationMethodCustom;
import com.street_art_explorer.auth_server.entity.ClientGrantType;
import com.street_art_explorer.auth_server.entity.ClientPostLogoutRedirectUri;
import com.street_art_explorer.auth_server.entity.ClientRedirectUri;
import com.street_art_explorer.auth_server.entity.ClientScope;
import com.street_art_explorer.auth_server.entity.OAuthClient;
import com.street_art_explorer.auth_server.repository.ClientAuthenticationMethodCustomRepository;
import com.street_art_explorer.auth_server.repository.ClientGrantTypeRepository;
import com.street_art_explorer.auth_server.repository.ClientPostLogoutRedirectUriRepository;
import com.street_art_explorer.auth_server.repository.ClientRedirectUriRepository;
import com.street_art_explorer.auth_server.repository.ClientScopeRepository;
import com.street_art_explorer.auth_server.repository.OAuthClientRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OAuthClientService {

    private final OAuthClientRepository oauthClientRepository;
    private final ClientAuthenticationMethodCustomRepository authenticationMethodCustomRepository;
    private final ClientGrantTypeRepository grantTypeRepository;
    private final ClientScopeRepository scopeRepository;
    private final ClientRedirectUriRepository redirectUriRepository;
    private final ClientPostLogoutRedirectUriRepository postLogoutRedirectUriRepository;
    private final PasswordEncoder passwordEncoder;
    private final OAuthClientConverter clientConverter;

    @Transactional
    public OAuthClientDto createOAuthClient(OAuthClientDto oauthClientDto) {
        if (oauthClientRepository.findByClientIdWithRelations(oauthClientDto.getClientId()).isPresent()) {
            throw new RuntimeException("Client already exists");
        }

        OAuthClient oauthClient = new OAuthClient();
        oauthClient.setClientId(oauthClientDto.getClientId());
        oauthClient.setClientSecret(passwordEncoder.encode(oauthClientDto.getClientSecret()));

        oauthClientRepository.save(oauthClient);

        Set<ClientAuthenticationMethodCustom> authMethods = authenticationMethodCustomRepository.findByAuthMethodIn(
                Optional.ofNullable(oauthClientDto.getAuthMethods()).orElse(Collections.emptySet())
        );

        Set<ClientGrantType> grantTypes = grantTypeRepository.findByGrantTypeIn(
                Optional.ofNullable(oauthClientDto.getGrantTypes()).orElse(Collections.emptySet())
        );

        Set<ClientRedirectUri> redirectUris = redirectUriRepository.findByRedirectUriIn(
                Optional.ofNullable(oauthClientDto.getRedirectUri()).orElse(Collections.emptySet())
        );

        Set<ClientPostLogoutRedirectUri> postLogoutRedirectUris = postLogoutRedirectUriRepository.findByPostLogoutRedirectUriIn(
                Optional.ofNullable(oauthClientDto.getPostLogoutRedirectUris()).orElse(Collections.emptySet())
        );

        Set<ClientScope> scopes = scopeRepository.findByScopeIn(
                Optional.ofNullable(oauthClientDto.getScopes()).orElse(Collections.emptySet())
        );

        oauthClient.setAuthenticationMethods(authMethods);
        oauthClient.setGrantTypes(grantTypes);
        oauthClient.setRedirectUris(redirectUris);
        oauthClient.setClientPostLogoutRedirectUris(postLogoutRedirectUris);
        oauthClient.setScopes(scopes);

        return clientConverter.oauthClientToOauthClientDTO(oauthClientRepository.save(oauthClient));
    }
}

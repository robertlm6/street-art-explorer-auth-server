package com.street_art_explorer.auth_server.service;

import com.street_art_explorer.auth_server.converter.OAuthClientConverter;
import com.street_art_explorer.auth_server.entity.OAuthClient;
import com.street_art_explorer.auth_server.repository.OAuthClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class CustomRegisteredClientRepository implements RegisteredClientRepository {

    private final OAuthClientRepository oauthClientRepository;
    private final OAuthClientConverter oauthClientConverter;

    @Override
    public void save(RegisteredClient registeredClient) {
        OAuthClient oauthClient = oauthClientConverter.registeredClientToOAuthClient(registeredClient);

        oauthClientRepository.save(oauthClient);
    }

    @Override
    @Transactional(readOnly = true)
    public RegisteredClient findById(String id) {
        OAuthClient oauthClient = oauthClientRepository.findById(Integer.valueOf(id))
                .orElseThrow(() -> new NoSuchElementException("Client with ID " + id + " not found"));
        return oauthClientConverter.oauthClientToRegisteredClient(oauthClient);
    }

    @Override
    @Transactional(readOnly = true)
    public RegisteredClient findByClientId(String clientId) {
        OAuthClient oauthClient = oauthClientRepository.findByClientIdWithRelations(clientId)
                .orElseThrow(() -> new NoSuchElementException("Client with ClientID " + clientId + " not found"));

        return oauthClientConverter.oauthClientToRegisteredClient(oauthClient);
    }
}

package com.street_art_explorer.auth_server.service;

import com.street_art_explorer.auth_server.dto.CreateClientDto;
import com.street_art_explorer.auth_server.dto.MessageDto;
import com.street_art_explorer.auth_server.entity.*;
import com.street_art_explorer.auth_server.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService implements RegisteredClientRepository {

    private final ClientRepository clientRepository;
    private final ClientAuthenticationMethodRepository clientAuthenticationMethodRepository;
    private final AuthorizationGrantTypeRepository authorizationGrantTypeRepository;
    private final RedirectUriRepository redirectUriRepository;
    private final ScopeRepository scopeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(RegisteredClient registeredClient) {
    }

    @Override
    public RegisteredClient findById(String id) {
        Client client = clientRepository.findByClientId(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return Client.toRegisteredClient(client);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Client client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return Client.toRegisteredClient(client);
    }

    
    public MessageDto create(CreateClientDto createClientDto) {
        Client client = clientFromDto(createClientDto);
        clientRepository.save(client);
        saveRelatedEntities(client, createClientDto);

        return new MessageDto("Client " + client.getClientId() + " saved.");
    }

    private void saveRelatedEntities(Client client, CreateClientDto createClientDto) {
        Set<ClientAuthenticationMethodEntity> authenticationMethods = createClientDto.getAuthenticationMethods().stream()
                .map(method -> new ClientAuthenticationMethodEntity(null, method.getMethod(), client))
                .collect(Collectors.toSet());

        Set<AuthorizationGrantTypeEntity> authorizationGrantTypes = createClientDto.getAuthorizationGrantTypes().stream()
                .map(grandType -> new AuthorizationGrantTypeEntity(null, grandType.getGrantType(), client))
                .collect(Collectors.toSet());

        Set<RedirectUriEntity> redirectUris = createClientDto.getRedirectUris().stream()
                .map(uri -> new RedirectUriEntity(null, uri.getUri(), client))
                .collect(Collectors.toSet());

        Set<ScopeEntity> scopes = createClientDto.getScopes().stream()
                .map(scope -> new ScopeEntity(null, scope.getScope(), client))
                .collect(Collectors.toSet());

        clientAuthenticationMethodRepository.saveAll(authenticationMethods);
        authorizationGrantTypeRepository.saveAll(authorizationGrantTypes);
        redirectUriRepository.saveAll(redirectUris);
        scopeRepository.saveAll(scopes);
    }

    private Client clientFromDto(CreateClientDto createClientDto) {
        return Client.builder()
                .clientId(createClientDto.getClientId())
                .clientSecret(passwordEncoder.encode(createClientDto.getClientSecret()))
                .requireProofKey(createClientDto.getRequireProofKey())
                .build();
    }
}

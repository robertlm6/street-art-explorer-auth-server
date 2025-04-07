package com.street_art_explorer.auth_server.service;

import com.street_art_explorer.auth_server.entity.OAuthUser;
import com.street_art_explorer.auth_server.entity.Role;
import com.street_art_explorer.auth_server.repository.OAuthUserRepository;
import com.street_art_explorer.auth_server.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final OAuthUserRepository oauthUserRepository;
    private final RoleRepository roleRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = new OidcUserService().loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String email = oidcUser.getAttribute("email");
        String username = generateUsernameFromEmail(email);

        if (oauthUserRepository.findByUsername(username).isEmpty()) {
            OAuthUser newUser = new OAuthUser();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setProvider(registrationId);
            newUser.setPassword(null);

            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

            newUser.setRole(userRole);
            oauthUserRepository.save(newUser);
        }

        return oidcUser;
    }

    private String generateUsernameFromEmail(String email) {
        return email != null ? email.split("@")[0] : UUID.randomUUID().toString();
    }
}

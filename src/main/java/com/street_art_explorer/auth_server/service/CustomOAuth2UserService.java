package com.street_art_explorer.auth_server.service;

import com.street_art_explorer.auth_server.entity.OAuthUser;
import com.street_art_explorer.auth_server.entity.Role;
import com.street_art_explorer.auth_server.repository.OAuthUserRepository;
import com.street_art_explorer.auth_server.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final OAuthUserRepository oauthUserRepository;
    private final RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String email = oAuth2User.getAttribute("email");
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

        return oAuth2User;
    }

    private String generateUsernameFromEmail(String email) {
        return email != null ? email.split("@")[0] : UUID.randomUUID().toString();
    }
}

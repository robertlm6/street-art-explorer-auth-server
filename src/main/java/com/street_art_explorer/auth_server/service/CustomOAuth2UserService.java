package com.street_art_explorer.auth_server.service;

import com.street_art_explorer.auth_server.entity.OAuthUser;
import com.street_art_explorer.auth_server.entity.Role;
import com.street_art_explorer.auth_server.repository.OAuthUserRepository;
import com.street_art_explorer.auth_server.repository.RoleRepository;
import com.street_art_explorer.auth_server.web_client.ResourceServerClient;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final OAuthUserRepository oauthUserRepository;
    private final RoleRepository roleRepository;
    private final ResourceServerClient resourceServerClient;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String email = oAuth2User.getAttribute("email");

        OAuthUser user = oauthUserRepository.findByEmail(email)
                .orElseGet(() -> createUser(email, registrationId));

        resourceServerClient.createAndUpdateUserInResource(user.getId(), user.getUsername(), user.getEmail());

        Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(user.getRole().getName()));

        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "email");
    }

    private OAuthUser createUser(String email, String provider) {
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

        OAuthUser user = new OAuthUser();
        user.setUsername(email.split("@")[0]);
        user.setPassword(null);
        user.setEmail(email);
        user.setProvider(provider);
        user.setRole(userRole);

        return oauthUserRepository.save(user);
    }
}

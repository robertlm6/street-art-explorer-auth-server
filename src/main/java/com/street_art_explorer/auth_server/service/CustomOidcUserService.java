package com.street_art_explorer.auth_server.service;

import com.street_art_explorer.auth_server.entity.OAuthUser;
import com.street_art_explorer.auth_server.entity.Role;
import com.street_art_explorer.auth_server.repository.OAuthUserRepository;
import com.street_art_explorer.auth_server.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Set;

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

        OAuthUser user = oauthUserRepository.findByEmail(email)
                .orElseGet(() -> createUser(email, registrationId));

        Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(user.getRole().getName()));

        return new DefaultOidcUser(authorities, oidcUser.getIdToken(), "email");
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

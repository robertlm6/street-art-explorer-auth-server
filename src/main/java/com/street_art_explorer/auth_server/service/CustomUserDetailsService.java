package com.street_art_explorer.auth_server.service;

import com.street_art_explorer.auth_server.entity.OAuthUser;
import com.street_art_explorer.auth_server.repository.OAuthUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private OAuthUserRepository oauthUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        OAuthUser user = oauthUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));

        if (user.getProvider() != null) {
            throw new BadCredentialsException("This user was created via " + user.getProvider() + ". Please, use the same login option.");
        }

        return user;
    }
}

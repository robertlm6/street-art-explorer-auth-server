package com.street_art_explorer.auth_server.service;

import com.street_art_explorer.auth_server.converter.OAuthUserConverter;
import com.street_art_explorer.auth_server.dto.OAuthUserDto;
import com.street_art_explorer.auth_server.entity.OAuthUser;
import com.street_art_explorer.auth_server.repository.OAuthUserRepository;
import com.street_art_explorer.auth_server.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OAuthUserService {

    private final OAuthUserRepository oauthUserRepository;
    private final RoleRepository roleRepository;
    private final OAuthUserConverter oauthUserConverter;

    @Transactional
    public OAuthUserDto createOAuthUser(OAuthUserDto oauthUserDto) {
        if (oauthUserRepository.findByUsername(oauthUserDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already in use");
        }

        if (oauthUserRepository.findByEmail(oauthUserDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        OAuthUser oauthUser = oauthUserConverter.userDtoToUser(oauthUserDto);
        return oauthUserConverter.userToUserDto(oauthUserRepository.save(oauthUser));
    }
}

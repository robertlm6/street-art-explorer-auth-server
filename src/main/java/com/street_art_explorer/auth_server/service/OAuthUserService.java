package com.street_art_explorer.auth_server.service;

import com.street_art_explorer.auth_server.converter.OAuthUserConverter;
import com.street_art_explorer.auth_server.dto.OAuthUserDto;
import com.street_art_explorer.auth_server.dto.OAuthUserRequestDto;
import com.street_art_explorer.auth_server.entity.OAuthUser;
import com.street_art_explorer.auth_server.repository.OAuthUserRepository;
import com.street_art_explorer.auth_server.web_client.ResourceServerClient;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OAuthUserService {

    private final OAuthUserRepository oauthUserRepository;
    private final OAuthUserConverter oauthUserConverter;
    private final ResourceServerClient resourceServerClient;

    @Transactional
    public OAuthUserDto createOAuthUser(OAuthUserRequestDto oAuthUserRequestDto) {
        if (oauthUserRepository.findByUsername(oAuthUserRequestDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already in use");
        }

        if (oauthUserRepository.findByEmail(oAuthUserRequestDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        OAuthUser oauthUser = oauthUserConverter.userRequestDtoToUser(oAuthUserRequestDto);
        OAuthUserDto oAuthUserDto = oauthUserConverter.userToUserDto(oauthUserRepository.save(oauthUser));

        resourceServerClient.createAndUpdateUserInResource(
                oAuthUserDto.getId(),
                oAuthUserDto.getUsername(),
                oAuthUserDto.getEmail()
        );

        return oAuthUserDto;
    }
}

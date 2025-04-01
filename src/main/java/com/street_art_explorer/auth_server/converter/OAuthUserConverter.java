package com.street_art_explorer.auth_server.converter;

import com.street_art_explorer.auth_server.dto.OAuthUserDto;
import com.street_art_explorer.auth_server.entity.OAuthUser;
import com.street_art_explorer.auth_server.entity.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OAuthUserConverter {

    private final PasswordEncoder passwordEncoder;

    public OAuthUserDto userToUserDto(OAuthUser oauthUser) {
        return new OAuthUserDto(oauthUser.getUsername(), null, oauthUser.getRole().getName());
    }

    public OAuthUser userDtoToUser(OAuthUserDto userDto) {
        OAuthUser user = new OAuthUser();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(new Role(null, userDto.getRole()));

        return user;
    }
}

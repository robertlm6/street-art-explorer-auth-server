package com.street_art_explorer.auth_server.converter;

import com.street_art_explorer.auth_server.dto.OAuthUserDto;
import com.street_art_explorer.auth_server.entity.OAuthUser;
import com.street_art_explorer.auth_server.entity.Role;
import com.street_art_explorer.auth_server.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OAuthUserConverter {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public OAuthUserDto userToUserDto(OAuthUser oauthUser) {
        return new OAuthUserDto(oauthUser.getUsername(), null, oauthUser.getRole().getName());
    }

    public OAuthUser userDtoToUser(OAuthUserDto userDto) {
        Role role = roleRepository.findByName(userDto.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        OAuthUser user = new OAuthUser();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(role);

        return user;
    }
}

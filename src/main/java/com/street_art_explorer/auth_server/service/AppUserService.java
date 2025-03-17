package com.street_art_explorer.auth_server.service;

import com.street_art_explorer.auth_server.dto.CreateAppUserDto;
import com.street_art_explorer.auth_server.dto.MessageDto;
import com.street_art_explorer.auth_server.entity.AppUser;
import com.street_art_explorer.auth_server.entity.Role;
import com.street_art_explorer.auth_server.enums.RoleName;
import com.street_art_explorer.auth_server.repository.AppUserRepository;
import com.street_art_explorer.auth_server.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public MessageDto createUser(CreateAppUserDto createAppUserDto) {
        AppUser appUser = AppUser.builder()
                .username(createAppUserDto.username())
                .password(passwordEncoder.encode(createAppUserDto.password()))
                .build();
        Role role = roleRepository.findByRoleName(RoleName.valueOf(createAppUserDto.role()))
                .orElseThrow(() -> new RuntimeException("Role not found"));
        appUser.setRole(role);
        appUserRepository.save(appUser);
        return new MessageDto("User " + appUser.getUsername() + " created successfully");
    }
}

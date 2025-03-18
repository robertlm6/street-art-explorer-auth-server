package com.street_art_explorer.auth_server.controller;

import com.street_art_explorer.auth_server.dto.CreateAppUserDto;
import com.street_art_explorer.auth_server.dto.MessageDto;
import com.street_art_explorer.auth_server.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService appUserService;

    @PostMapping("/create")
    public ResponseEntity<MessageDto> createUser(@RequestBody CreateAppUserDto createAppUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appUserService.createUser(createAppUserDto));
    }
}

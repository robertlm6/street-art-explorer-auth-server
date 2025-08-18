package com.street_art_explorer.auth_server.controller;

import com.street_art_explorer.auth_server.dto.OAuthUserDto;
import com.street_art_explorer.auth_server.dto.OAuthUserRequestDto;
import com.street_art_explorer.auth_server.service.OAuthUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class OAuthUserController {

    private final OAuthUserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody OAuthUserRequestDto userRequestDtoDto) {
        try {
            OAuthUserDto oauthUserDto = userService.createOAuthUser(userRequestDtoDto);
            return ResponseEntity.ok(oauthUserDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}

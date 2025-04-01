package com.street_art_explorer.auth_server.controller;

import com.street_art_explorer.auth_server.dto.OAuthClientDto;
import com.street_art_explorer.auth_server.service.OAuthClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class OAuthClientController {

    private final OAuthClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerClient(@RequestBody OAuthClientDto clientDto) {
        try {
            OAuthClientDto oAuthClientDto = clientService.createOAuthClient(clientDto);
            return ResponseEntity.ok(oAuthClientDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

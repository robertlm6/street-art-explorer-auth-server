package com.street_art_explorer.auth_server.controller;

import com.street_art_explorer.auth_server.dto.CreateClientDto;
import com.street_art_explorer.auth_server.dto.MessageDto;
import com.street_art_explorer.auth_server.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<MessageDto> create(@RequestBody CreateClientDto createClientDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(createClientDto));
    }
}

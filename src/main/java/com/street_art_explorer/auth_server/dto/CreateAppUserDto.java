package com.street_art_explorer.auth_server.dto;

public record CreateAppUserDto (
    String username,
    String password,
    String role) {}

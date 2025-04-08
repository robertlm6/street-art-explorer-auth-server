package com.street_art_explorer.auth_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OAuthUserDto {

    private String username;
    private String password;
    private String email;
    private String role;
}

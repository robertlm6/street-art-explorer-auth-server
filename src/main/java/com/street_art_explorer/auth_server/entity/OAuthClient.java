package com.street_art_explorer.auth_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OAuthClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String clientId;
    @Column(nullable = false)
    private String clientSecret;
    @ManyToMany
    @JoinTable(
            name = "client_authentication_methods",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "auth_method_id")
    )
    private Set<ClientAuthenticationMethodCustom> authenticationMethods = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "client_grant_types",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "grant_type_id")
    )
    private Set<ClientGrantType> grantTypes = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "client_redirect_uris",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "redirect_uri_id")
    )
    private Set<ClientRedirectUri> redirectUris = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "client_scopes",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "scope_id")
    )
    private Set<ClientScope> scopes = new HashSet<>();
}

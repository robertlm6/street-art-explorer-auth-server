package com.street_art_explorer.auth_server.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
            name = "client_post_logout_redirect_uris",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "post_logout_redirect_uri_id")
    )
    private Set<ClientPostLogoutRedirectUri> clientPostLogoutRedirectUris = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "client_scopes",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "scope_id")
    )
    private Set<ClientScope> scopes = new HashSet<>();
}

package com.street_art_explorer.auth_server.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.street_art_explorer.auth_server.entity.ClientPostLogoutRedirectUri;

@Repository
public interface ClientPostLogoutRedirectUriRepository extends JpaRepository<ClientPostLogoutRedirectUri, Integer> {

    Set<ClientPostLogoutRedirectUri> findByPostLogoutRedirectUriIn(Set<String> postLogoutRedirectUris);
}

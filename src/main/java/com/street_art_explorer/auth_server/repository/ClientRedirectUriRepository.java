package com.street_art_explorer.auth_server.repository;

import com.street_art_explorer.auth_server.entity.ClientRedirectUri;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ClientRedirectUriRepository extends JpaRepository<ClientRedirectUri, Integer> {
    Set<ClientRedirectUri> findByRedirectUriIn(Set<String> redirectUris);
}

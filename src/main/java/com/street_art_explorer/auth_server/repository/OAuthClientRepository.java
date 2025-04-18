package com.street_art_explorer.auth_server.repository;

import com.street_art_explorer.auth_server.entity.OAuthClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthClientRepository extends JpaRepository<OAuthClient, Integer> {

    @Query("SELECT c FROM OAuthClient c " +
            "LEFT JOIN FETCH c.authenticationMethods " +
            "LEFT JOIN FETCH c.grantTypes " +
            "LEFT JOIN FETCH c.redirectUris " +
            "LEFT JOIN FETCH c.scopes " +
            "WHERE c.clientId = :clientId")
    Optional<OAuthClient> findByClientIdWithRelations(@Param("clientId") String clientId);
}

package com.street_art_explorer.auth_server.repository;

import com.street_art_explorer.auth_server.entity.ClientAuthenticationMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface ClientAuthenticationMethodRepository extends JpaRepository<ClientAuthenticationMethodEntity, Serializable> {
}

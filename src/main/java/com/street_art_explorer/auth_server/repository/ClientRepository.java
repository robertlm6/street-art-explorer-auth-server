package com.street_art_explorer.auth_server.repository;

import com.street_art_explorer.auth_server.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Serializable> {

    Optional<Client> findByClientId(String clientId);
}

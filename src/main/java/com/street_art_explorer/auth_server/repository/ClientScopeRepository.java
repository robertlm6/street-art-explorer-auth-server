package com.street_art_explorer.auth_server.repository;

import com.street_art_explorer.auth_server.entity.ClientScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ClientScopeRepository extends JpaRepository<ClientScope, Integer> {

    Set<ClientScope> findByScopeIn(Set<String> scopes);
}

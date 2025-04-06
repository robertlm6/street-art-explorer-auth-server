package com.street_art_explorer.auth_server.repository;

import com.street_art_explorer.auth_server.entity.ClientGrantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ClientGrantTypeRepository extends JpaRepository<ClientGrantType, Integer> {

    Set<ClientGrantType> findByGrantTypeIn(Set<String> grantTypes);
}

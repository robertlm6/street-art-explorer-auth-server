package com.street_art_explorer.auth_server.repository;

import com.street_art_explorer.auth_server.entity.ClientAuthenticationMethodCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ClientAuthenticationMethodCustomRepository extends JpaRepository<ClientAuthenticationMethodCustom, Integer> {

    Set<ClientAuthenticationMethodCustom> findByAuthMethodIn(Set<String> authMethods);
}

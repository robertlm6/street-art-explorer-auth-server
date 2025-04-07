package com.street_art_explorer.auth_server.repository;

import com.street_art_explorer.auth_server.entity.OAuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthUserRepository extends JpaRepository<OAuthUser, Integer> {

    Optional<OAuthUser> findByUsername(String username);

    Optional<OAuthUser> findByEmail(String email);
}

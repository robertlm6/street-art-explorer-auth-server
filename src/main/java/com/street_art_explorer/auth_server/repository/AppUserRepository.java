package com.street_art_explorer.auth_server.repository;

import com.street_art_explorer.auth_server.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Serializable> {

    Optional<AppUser> findByUsername(String username);
}

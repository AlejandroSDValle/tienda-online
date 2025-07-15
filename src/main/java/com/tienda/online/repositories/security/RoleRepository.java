package com.tienda.online.repositories.security;

import com.tienda.online.entities.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String defaultRole);
}

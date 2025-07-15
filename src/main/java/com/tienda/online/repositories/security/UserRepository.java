package com.tienda.online.repositories.security;

import com.tienda.online.entities.security.Role;
import com.tienda.online.entities.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findByEmailStartingWith(String email);

    List<User> findByRoleIn(List<Role> roles);

    Optional<User>  findByEmail(String email);
}

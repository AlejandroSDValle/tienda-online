package com.tienda.online.services;

import com.tienda.online.entities.security.Role;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findDefaultRole();
}

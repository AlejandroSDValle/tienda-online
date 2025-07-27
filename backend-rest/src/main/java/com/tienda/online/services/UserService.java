package com.tienda.online.services;

import com.tienda.online.dto.auth.UserResponse;
import com.tienda.online.entities.security.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findOneByUsername(String username);

    List<UserResponse> searchEmployees();

    List<UserResponse> searchUserByEmail(String email);
}

package com.tienda.online.services.impl;

import com.tienda.online.dto.auth.UserResponse;
import com.tienda.online.entities.security.Role;
import com.tienda.online.entities.security.User;
import com.tienda.online.exceptions.ObjectNotFoundException;
import com.tienda.online.repositories.security.RoleRepository;
import com.tienda.online.repositories.security.UserRepository;
import com.tienda.online.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findOneByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserResponse> searchEmployees() {
        List<Role> roles = List.of(
                roleRepository.findByName("ASSISTANT_ADMINISTRATOR").orElseThrow(()-> new ObjectNotFoundException("Role does not exist")),
                roleRepository.findByName("ADMINISTRATOR").orElseThrow(()-> new ObjectNotFoundException("Role does not exist"))
        );
        List<User> users = userRepository.findByRoleIn(roles);
        return users.stream().map(UserServiceImpl::getUserResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> searchUserByEmail(String email) {
        List<User> usersFound = userRepository.findByEmailStartingWith(email);
        List<UserResponse> userResponses = new ArrayList<>();
        usersFound.forEach(user -> {
            UserResponse userResponse = getUserResponse(user);
            userResponses.add(userResponse);
        });
        return userResponses;
    }

    public static UserResponse getUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setUsername(user.getUsername());
        userResponse.setName(user.getName());
        userResponse.setRole(user.getRole());
        Collection<? extends GrantedAuthority> grantedAuthorities = user.getAuthorities();
        if (grantedAuthorities != null) {
            List<String> authorityNames = grantedAuthorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            userResponse.setAuthorities(authorityNames);
        }
        return userResponse;
    }

}

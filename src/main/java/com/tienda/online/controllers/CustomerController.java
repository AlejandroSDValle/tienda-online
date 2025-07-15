package com.tienda.online.controllers;

import com.tienda.online.dto.auth.UserResponse;
import com.tienda.online.services.UserService;
import com.tienda.online.services.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> searchUserByEmail(@RequestParam String email){
        return ResponseEntity.ok(userService.searchUserByEmail(email));
    }

    @GetMapping("/search/employees")
    public ResponseEntity<List<UserResponse>> searchEmployees(){
        return ResponseEntity.ok(userService.searchEmployees());
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> findMyProfile(){
        UserResponse user = authenticationService.findLoggedInUser();
        return ResponseEntity.ok(user);
    }

}

package com.tienda.online.controllers;

import com.tienda.online.dto.auth.*;
import com.tienda.online.services.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestParam String jwt){
        boolean isTokenValid = authenticationService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }

    @PostMapping
    public ResponseEntity<String> registerOne(@RequestBody SaveUser newUser){
        authenticationService.registerOneCustomer(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("Check your email to verify your account.");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest){

        AuthenticationResponse rsp = authenticationService.login(authenticationRequest);
        return ResponseEntity.ok(rsp);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam String token){
        authenticationService.verifyAccount(token);
        return ResponseEntity.status(HttpStatus.CREATED).body("Your account has been verified.");
    }

    @PostMapping("/verify-with-email")
    public ResponseEntity<String> verifyAccountWithEmail(@RequestBody EmailRequest email){
        authenticationService.verifyAccountWithEmail(email);
        return ResponseEntity.status(HttpStatus.CREATED).body("Your account has been verified.");
    }

    @PostMapping("/generate-token")
    public ResponseEntity<String> generateAnotherToken(@RequestBody EmailRequest email){
        authenticationService.generateAnotherToken(email);
        return ResponseEntity.status(HttpStatus.CREATED).body("Check your email to verify your account.");
    }
}

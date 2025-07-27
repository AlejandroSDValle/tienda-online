package com.tienda.online.services.auth;

import com.tienda.online.dto.Email;
import com.tienda.online.dto.auth.*;
import com.tienda.online.entities.VerificationToken;
import com.tienda.online.entities.security.Role;
import com.tienda.online.entities.security.User;
import com.tienda.online.exceptions.InvalidPasswordException;
import com.tienda.online.exceptions.ObjectNotFoundException;
import com.tienda.online.exceptions.UsernameAlreadyExistsException;
import com.tienda.online.repositories.VerificationTokenRepository;
import com.tienda.online.repositories.security.UserRepository;
import com.tienda.online.services.EmailService;
import com.tienda.online.services.RoleService;
import com.tienda.online.services.UserService;
import com.tienda.online.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    public UserResponse findLoggedInUser() {
        Authentication auth = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        User user = userService.findOneByUsername(username)
                .orElseThrow(()-> new ObjectNotFoundException("User not found. Username: " + username));
        return UserServiceImpl.getUserResponse(user);
    }

    @Transactional
    public void registerOneCustomer(SaveUser newUser){
        if (userRepository.existsByUsername(newUser.getUsername()) || userRepository.existsByEmail(newUser.getEmail())) {
            throw new UsernameAlreadyExistsException("Username or email is already taken");
        }

        validatePassword(newUser);

        User user = new User();
        user.setEmail(newUser.getEmail());
        user.setUsername(newUser.getUsername());
        user.setName(newUser.getName());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        Role defaultRole = roleService.findDefaultRole()
                .orElseThrow(()-> new ObjectNotFoundException("Role not found. Default Role"));
        user.setRole(defaultRole);
        userRepository.save(user);

        VerificationToken token = new VerificationToken();
        token.setUser(user);
        token.setToken(generateToken());
        token.setCreatedAt(LocalDateTime.now());
        token.setExpiresAt(LocalDateTime.now().plusDays(2));
        tokenRepository.save(token);

        //Enviar Email
        Email email = new Email();
        email.setRecipient(newUser.getEmail());
        email.setSubject("LAPROV - Confirm your account");
        email.setName(newUser.getName());
        email.setToken(token.getToken());
        emailService.sendMail(email);
    }

    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword()
        );
        authenticationManager.authenticate(authentication);

        UserDetails user = userService.findOneByUsername(authRequest.getUsername()).get();
        String jwt = jwtService.generateToken(user, generateExtraClaims((User) user));

        AuthenticationResponse authRsp = new AuthenticationResponse();
        authRsp.setJwt(jwt);
        return authRsp;
    }

    public void verifyAccount(String token){
        // Eliminar todos los tokens expirados de la base de datos
        tokenRepository.deleteAllByExpiresAtBefore(LocalDateTime.now());

        VerificationToken tokenDB = tokenRepository.findByToken(token).orElseThrow(()-> new ObjectNotFoundException("Token: " + token + " not found or expired"));

        User user = tokenDB.getUser();
        user.setConfirmed(true);
        userRepository.save(user);

        tokenRepository.delete(tokenDB);
    }

    @Transactional
    public void verifyAccountWithEmail(EmailRequest email){
        User user = userRepository.findByEmail(email.getEmail()).orElseThrow(()-> new ObjectNotFoundException("Token: " + email.getEmail() + " not found or expired"));
        if(user.isEnabled()){
            throw new RuntimeException("Your account is already verified");
        }
        user.setConfirmed(true);
        userRepository.save(user);
    }

    @Transactional
    public void generateAnotherToken(EmailRequest email){
        User user = userRepository.findByEmail(email.getEmail()).orElseThrow(()-> new ObjectNotFoundException("Token: " + email.getEmail() + " not found or expired"));

        if(user.isEnabled()){
            throw new RuntimeException("Your account is already verified");
        }

        VerificationToken token = new VerificationToken();
        token.setUser(user);
        token.setToken(generateToken());
        token.setCreatedAt(LocalDateTime.now());
        token.setExpiresAt(LocalDateTime.now().plusDays(2));
        tokenRepository.save(token);

        //Send Email
        Email sendEmail = new Email();
        sendEmail.setRecipient(user.getEmail());
        sendEmail.setSubject("LAPROV - Confirm your account");
        sendEmail.setName(user.getName());
        sendEmail.setToken(token.getToken());
        emailService.sendMail(sendEmail);
    }

    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("role", user.getRole().getName());
        return extraClaims;
    }

    private void validatePassword(SaveUser dto) {
        if(!StringUtils.hasText(dto.getPassword()) || !StringUtils.hasText(dto.getRepeatedPassword())){
            throw new InvalidPasswordException("Passwords don't match");
        }

        if(!dto.getPassword().equals(dto.getRepeatedPassword())){
            throw new InvalidPasswordException("Passwords don't match");
        }
    }

    public boolean validateToken(String jwt) {
        try{
            jwtService.extractUsername(jwt);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public String generateToken() {
        int randomNumber = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(randomNumber);
    }
}

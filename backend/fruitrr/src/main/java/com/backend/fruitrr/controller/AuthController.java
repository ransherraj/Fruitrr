package com.backend.fruitrr.controller;


import com.backend.fruitrr.model.Role;
import com.backend.fruitrr.model.User;
import com.backend.fruitrr.payload.LoginRequest;
import com.backend.fruitrr.payload.LoginResponse;
import com.backend.fruitrr.payload.SignUpRequest;
import com.backend.fruitrr.repository.UserRepository;
import com.backend.fruitrr.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }

        String token = tokenProvider.generateToken(user);
        user.setToken(token);
        userRepository.save(user);

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setEmail(signUpRequest.getEmail());
        user.setRoles(Collections.singletonList(new Role(null, "ROLE_USER")));

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }
}

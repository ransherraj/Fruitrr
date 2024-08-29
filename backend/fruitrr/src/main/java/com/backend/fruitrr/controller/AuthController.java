package com.backend.fruitrr.controller;


import com.backend.fruitrr.model.Role;
import com.backend.fruitrr.model.User;
import com.backend.fruitrr.payload.LoginRequest;
import com.backend.fruitrr.response.LoginResponse;
import com.backend.fruitrr.payload.SignUpRequest;
import com.backend.fruitrr.repository.UserRepository;
import com.backend.fruitrr.response.ResponseData;
import com.backend.fruitrr.response.SignUpResponse;
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
    public ResponseData<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {

        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            ResponseData<LoginResponse> res = new ResponseData<>(false, "Invalid username or password", Collections.emptyList(), 401, 0);
            /*res.Message="Invalid username or password";
            res.StatusCode=401;
            res.IsSuccess=false;
            res.Entity=null;*/
            return res;
        }

        String token = tokenProvider.generateToken(user);
        user.setToken(token);
        userRepository.save(user);

        ResponseData<LoginResponse> res = new ResponseData<>(true, "Login Successful", Collections.emptyList(), 200, 1, new LoginResponse(token));
        /*res.StatusCode=200;
        res.Entity = new LoginResponse(token);
        res.Message="Login Successful";
        res.IsSuccess=true;*/
        //res.Token = new LoginResponse(token);
        return res;
    }

    @PostMapping("/signup")
    public ResponseData<SignUpResponse> registerUser(@RequestBody SignUpRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            ResponseData<SignUpResponse> res = new ResponseData<>(false, "Username is already taken!", Collections.emptyList(), 401, 0);
            /*res.Message="Username is already taken!";
            res.StatusCode=401;
            res.IsSuccess=false;
            res.Entity=null;*/
            return res;
        }

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setEmail(signUpRequest.getEmail());
        //user.setRoles(Collections.singletonList(new Role(null, "ROLE_USER")));

        userRepository.save(user);

        ResponseData<SignUpResponse> res = new ResponseData<>(true, "User registered successfully", Collections.emptyList(), 200, 0);

        /*res.StatusCode=200;
        res.Message="User registered successfully";
        res.IsSuccess=true;*/
        return res;
    }
}

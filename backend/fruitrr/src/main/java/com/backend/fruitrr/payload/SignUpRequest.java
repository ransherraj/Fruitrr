package com.backend.fruitrr.payload;


import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String password;
    private String email;
}


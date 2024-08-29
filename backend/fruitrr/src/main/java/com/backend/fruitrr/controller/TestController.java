package com.backend.fruitrr.controller;

import com.backend.fruitrr.model.User;
import com.backend.fruitrr.response.ResponseData;
import com.backend.fruitrr.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class TestController {

    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @GetMapping("/users")
    public ResponseData<User> findEmployees(){
        List<User> users = customUserDetailsService.findUsers();
        ResponseData<User> res = new ResponseData<>(true, "All users", users, 200, 0);
        return res;
        };
    }


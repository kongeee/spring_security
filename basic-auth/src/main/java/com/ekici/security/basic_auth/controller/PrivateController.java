package com.ekici.security.basic_auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private")
public class PrivateController {

    @GetMapping
    private String greet(){return "Hello from PRIVATE";}

    @GetMapping("/user")
    private String greetUser(){return "Hello from PRIVATE for USER";}

    @GetMapping("/admin")
    private String greetAdmin(){return "Hello from PRIVATE for ADMIN";}
}


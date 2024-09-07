package com.ekici.security.basic_auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping
    private String greet(){return "Hello from PUBLIC";}

    @GetMapping("/user")
    private String greetUser(){return "Hello from PUBLIC for USER";}

    @GetMapping("/admin")
    private String greetAdmin(){return "Hello from PUBLIC for ADMIN";}
}

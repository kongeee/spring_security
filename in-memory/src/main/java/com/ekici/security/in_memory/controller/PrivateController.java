package com.ekici.security.in_memory.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/private")
public class PrivateController {

    @GetMapping
    //auth bilgileri yanlissa 401 Unauthorized, yetki yoksa 403 forbidden hatasi verir
    public String greet(){
        return "Hello from PRIVATE";
    }

    //PreAuthorize yerine SecurityConfig filterChain'de authorizeHttpRequests eklenebilir
    //@PreAuthorize("hasRole('ADMIN')") //rol kontrolu
    @GetMapping("/admin")
    public String greetAdmin(){
        return "Hello from PRIVATE for ADMIN";
    }

    //@PreAuthorize("hasRole('USER')")//rol kontrolu
    @GetMapping("/user")
    public String greetUser(){
        return "Hello from PRIVATE for USER";
    }
}

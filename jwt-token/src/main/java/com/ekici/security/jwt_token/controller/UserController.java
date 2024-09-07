package com.ekici.security.jwt_token.controller;

import com.ekici.security.jwt_token.dto.AuthRequest;
import com.ekici.security.jwt_token.dto.CreateUserRequest;
import com.ekici.security.jwt_token.model.User;
import com.ekici.security.jwt_token.service.AuthService;
import com.ekici.security.jwt_token.service.JwtService;
import com.ekici.security.jwt_token.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, AuthService authService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authService = authService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome!";
    }

    //register
    @PostMapping("/addNewUser")
    public User addUser(@RequestBody CreateUserRequest request){
        return userService.createUser(request);
    }

    //login
    @PostMapping("/generateToken")
    public String generateToken(@RequestBody AuthRequest request){
        return authService.generateToken(request);

    }

    @GetMapping("/user")
    public String helloUser(){
        return "USER endpoint!";
    }

    @GetMapping("/admin")
    public String helloAdmin(){
        return "ADMIN endpoint!";
    }



}

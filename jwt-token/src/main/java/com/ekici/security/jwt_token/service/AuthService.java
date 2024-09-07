package com.ekici.security.jwt_token.service;

import com.ekici.security.jwt_token.dto.AuthRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String generateToken(AuthRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(  //provider i kullanir
                        request.username(), request.password()
                )
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(request.username());
        }

        throw new UsernameNotFoundException("invalid username");
    }
}

package com.ekici.security.jwt_token.dto;

public record AuthRequest(
        String username,
        String password
) {
}

package com.ekici.security.jwt_token.dto;

import com.ekici.security.jwt_token.model.Role;
import java.util.Set;

public record CreateUserRequest(
        String name,
        String username,
        String password,
        Set<Role> authorities
) {
}

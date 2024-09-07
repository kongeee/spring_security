package com.ekici.security.basic_auth.dto;

import com.ekici.security.basic_auth.model.Role;

import java.util.Set;

public record CreateUserRequest (
        String name,
        String username,
        String password,
        Set<Role> authorities
) {
}

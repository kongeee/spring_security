package com.ekici.security.jwt_token.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {//rolleri standardize etmek icin
    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();//enum name
    }

}

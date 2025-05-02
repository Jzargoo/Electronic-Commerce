package com.jzargo.common;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    BUYER, SELLER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }

}
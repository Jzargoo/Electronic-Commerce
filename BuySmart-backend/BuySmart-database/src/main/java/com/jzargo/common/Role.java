package com.jzargo.common;

import org.springframework.security.core.GrantedAuthority;
public enum Role implements GrantedAuthority {
    GUEST, BUYER, SELLER;

    @Override
    public String getAuthority() {
        return name();
    }

}
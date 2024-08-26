package com.laofan.iantha.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class FanUser  extends org.springframework.security.core.userdetails.User {

    private String identify;

    private Long ident;

    private String phone;

    private String currentRole;

    public FanUser(String username, String password, Collection<? extends GrantedAuthority> authorities,Long  ident) {
        super(username, password, authorities);
        this.ident=ident;
    }

    public Long getIdent() {
        return ident;
    }

    public void setIdent(Long ident) {
        this.ident = ident;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCurrentRole() {
        return currentRole;
    }

    public void setCurrentRole(String currentRole) {
        this.currentRole = currentRole;
    }
}

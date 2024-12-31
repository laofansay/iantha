package com.laofan.iantha.stripe.dto;

import com.laofan.iantha.service.dto.UserDTO;
import com.laofan.iantha.web.rest.AccountResource;

public class Member extends UserDTO {

    private String email;
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

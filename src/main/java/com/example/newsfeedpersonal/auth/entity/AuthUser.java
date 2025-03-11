package com.example.newsfeedpersonal.auth.entity;

import lombok.Getter;

@Getter
public class AuthUser {

    private Long id;
    private String email;
    private String name;
    private String password;

    public AuthUser(Long id, String email, String name, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }
}

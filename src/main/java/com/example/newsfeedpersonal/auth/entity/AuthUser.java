package com.example.newsfeedpersonal.auth.entity;

import lombok.Getter;

@Getter
public class AuthUser {

    private Long id;
    private String email;
    private String name;

    public AuthUser(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
}

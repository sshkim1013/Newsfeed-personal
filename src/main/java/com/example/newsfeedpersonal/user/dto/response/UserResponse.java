package com.example.newsfeedpersonal.user.dto.response;

import lombok.Getter;

@Getter
public class UserResponse {

    private Long id;
    private String email;
    private String name;

    public UserResponse(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public UserResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

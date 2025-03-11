package com.example.newsfeedpersonal.user.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)  // null 값인 필드는 제외
public class UserResponse {

    private final Long id;
    private final String email;
    private final String name;

    public UserResponse(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public UserResponse(Long id, String name) {
        this.id = id;
        this.email = null;
        this.name = name;
    }
}

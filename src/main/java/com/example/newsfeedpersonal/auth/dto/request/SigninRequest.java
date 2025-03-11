package com.example.newsfeedpersonal.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SigninRequest {

    @NotBlank @Email
    private String email;

    @NotBlank
    private String password;
}

package com.example.newsfeedpersonal.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class WithdrawRequest {

    @NotBlank
    @Email(message = "이메일 형식이어야 합니다.")
    private String email;

    @NotBlank
    private String password;
}

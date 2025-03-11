package com.example.newsfeedpersonal.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdatePasswordRequest {

    private String oldPassword;

    @NotBlank
    @Size(min = 8, max = 24, message = "비밀번호는 8자 이상 24자 이하이어야 합니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{8,24}$", message = "비밀번호는 영어 대소문자, 숫자, 특수문자를 포함해야 하며 특수문자는 +, /, \\; : - _ ^ & ( ) < > 제외해야 합니다.")
    private String newPassword;
}

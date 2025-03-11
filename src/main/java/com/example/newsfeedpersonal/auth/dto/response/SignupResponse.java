package com.example.newsfeedpersonal.auth.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignupResponse {

    private final String jwt;
}

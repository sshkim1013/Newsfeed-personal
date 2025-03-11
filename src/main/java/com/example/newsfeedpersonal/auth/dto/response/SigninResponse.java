package com.example.newsfeedpersonal.auth.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SigninResponse {

    private final String bearerJwt;
}

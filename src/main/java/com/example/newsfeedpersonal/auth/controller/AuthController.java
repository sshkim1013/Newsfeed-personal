package com.example.newsfeedpersonal.auth.controller;

import com.example.newsfeedpersonal.auth.dto.request.SigninRequest;
import com.example.newsfeedpersonal.auth.dto.request.SignupRequest;
import com.example.newsfeedpersonal.auth.dto.response.SigninResponse;
import com.example.newsfeedpersonal.auth.dto.response.SignupResponse;
import com.example.newsfeedpersonal.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok(authService.signup(signupRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<SigninResponse> signin(@RequestBody SigninRequest signinRequest) {
        return ResponseEntity.ok(authService.signin(signinRequest));
    }
}

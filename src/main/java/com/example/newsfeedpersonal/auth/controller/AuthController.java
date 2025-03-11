package com.example.newsfeedpersonal.auth.controller;

import com.example.newsfeedpersonal.auth.dto.request.SigninRequest;
import com.example.newsfeedpersonal.auth.dto.request.SignupRequest;
import com.example.newsfeedpersonal.auth.dto.response.SigninResponse;
import com.example.newsfeedpersonal.auth.dto.response.SignupResponse;
import com.example.newsfeedpersonal.auth.service.AuthService;
import com.example.newsfeedpersonal.user.dto.request.WithdrawRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody @Valid SignupRequest signupRequest) {
        return ResponseEntity.ok(authService.signup(signupRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<SigninResponse> signin(@RequestBody @Valid SigninRequest signinRequest) {
        return ResponseEntity.ok(authService.signin(signinRequest));
    }

    @DeleteMapping("/withdraw")
    public void withdraw (@RequestBody @Valid WithdrawRequest drawRequest) {
        authService.withdraw(drawRequest);
    }
}

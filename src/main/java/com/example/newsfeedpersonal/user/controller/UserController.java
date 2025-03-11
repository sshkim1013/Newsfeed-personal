package com.example.newsfeedpersonal.user.controller;

import com.example.newsfeedpersonal.auth.annotation.Auth;
import com.example.newsfeedpersonal.auth.entity.AuthUser;
import com.example.newsfeedpersonal.user.dto.request.UserUpdatePasswordRequest;
import com.example.newsfeedpersonal.user.dto.request.UserUpdateRequest;
import com.example.newsfeedpersonal.user.dto.response.UserResponse;
import com.example.newsfeedpersonal.user.dto.response.UserUpdateResponse;
import com.example.newsfeedpersonal.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> findUser(
            @PathVariable Long userId,
            @Auth AuthUser authUser
    ) {
        return ResponseEntity.ok(userService.findUser(userId, authUser));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserUpdateResponse> updateProfile(
            @PathVariable Long userId,
            @Auth AuthUser authUser,
            @RequestBody @Valid UserUpdateRequest request
    ) {
        return ResponseEntity.ok(userService.updateProfile(userId, authUser, request));
    }

    @PutMapping("/password")
    public void updatePassword(
            @PathVariable Long userId,
            @Auth AuthUser authUser,
            @RequestBody @Valid UserUpdatePasswordRequest request
    ) {
        userService.updatePassword(userId, authUser, request);
    }
}

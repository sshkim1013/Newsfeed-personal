package com.example.newsfeedpersonal.follow.controller;

import com.example.newsfeedpersonal.auth.annotation.Auth;
import com.example.newsfeedpersonal.auth.entity.AuthUser;
import com.example.newsfeedpersonal.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follows/{targetUserId}")
public class FollowController {

    private final FollowService followService;

    @PostMapping
    public void follow(
            @Auth AuthUser authUser,
            @PathVariable Long targetUserId
    ) {
        followService.follow(authUser, targetUserId);
    }

    @DeleteMapping
    public void unfollow(
            @Auth AuthUser authUser,
            @PathVariable Long targetUserId
    ) {
        followService.unfollow(authUser, targetUserId);
    }
}

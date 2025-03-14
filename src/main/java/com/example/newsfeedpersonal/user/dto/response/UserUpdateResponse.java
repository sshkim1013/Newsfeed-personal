package com.example.newsfeedpersonal.user.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UserUpdateResponse {

    private final Long id;
    private final String email;
    private final String name;
    private final int followerUsers;
    private final int followingUsers;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime updatedAt;
}

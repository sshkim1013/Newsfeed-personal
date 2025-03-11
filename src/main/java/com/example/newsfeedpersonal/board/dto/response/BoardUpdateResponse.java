package com.example.newsfeedpersonal.board.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class BoardUpdateResponse {
    private final Long id;
    private final Long userId;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}

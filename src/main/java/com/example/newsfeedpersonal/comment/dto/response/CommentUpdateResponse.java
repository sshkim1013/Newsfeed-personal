package com.example.newsfeedpersonal.comment.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommentUpdateResponse {

    private final Long id;
    private final Long userId;
    private final Long boardId;
    private final String content;
    private final int likes;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}

package com.example.newsfeedpersonal.like.comment.controller;

import com.example.newsfeedpersonal.auth.annotation.Auth;
import com.example.newsfeedpersonal.auth.entity.AuthUser;
import com.example.newsfeedpersonal.like.comment.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/{boardId}/comments/{commentId}")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/like")
    public void like(
            @Auth AuthUser authUser,
            @PathVariable Long boardId,
            @PathVariable Long commentId
    ) {
        commentLikeService.like(authUser, boardId, commentId);
    }

    @DeleteMapping("/unlike")
    public void unlike(
            @Auth AuthUser authUser,
            @PathVariable Long boardId,
            @PathVariable Long commentId
    ) {
        commentLikeService.unlike(authUser, boardId, commentId);
    }

}

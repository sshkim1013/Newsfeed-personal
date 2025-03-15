package com.example.newsfeedpersonal.like.board.controller;

import com.example.newsfeedpersonal.auth.annotation.Auth;
import com.example.newsfeedpersonal.auth.entity.AuthUser;
import com.example.newsfeedpersonal.like.board.service.BoardLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/{boardId}")
public class BoardLikeController {

    private final BoardLikeService boardLikeService;

    @PostMapping("/like")
    public void like(
            @Auth AuthUser authUser,
            @PathVariable Long boardId
    ) {
        boardLikeService.like(authUser, boardId);
    }

    @DeleteMapping("/unlike")
    public void unlike(
            @Auth AuthUser authUser,
            @PathVariable Long boardId
    ) {
        boardLikeService.unlike(authUser, boardId);
    }

}

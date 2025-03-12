package com.example.newsfeedpersonal.comment.controller;

import com.example.newsfeedpersonal.auth.annotation.Auth;
import com.example.newsfeedpersonal.auth.entity.AuthUser;
import com.example.newsfeedpersonal.comment.dto.request.CommentSaveRequest;
import com.example.newsfeedpersonal.comment.dto.request.CommentUpdateRequest;
import com.example.newsfeedpersonal.comment.dto.response.CommentResponse;
import com.example.newsfeedpersonal.comment.dto.response.CommentSaveResponse;
import com.example.newsfeedpersonal.comment.dto.response.CommentUpdateResponse;
import com.example.newsfeedpersonal.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/{boardId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentSaveResponse> save(
            @Auth AuthUser authUser,
            @RequestBody CommentSaveRequest request,
            @PathVariable Long boardId
    ) {
        return new ResponseEntity<>(commentService.save(authUser, request, boardId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> findAll(@PathVariable Long boardId) {
        return ResponseEntity.ok(commentService.findAll(boardId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentUpdateResponse> update(
            @Auth AuthUser authUser,
            @RequestBody CommentUpdateRequest request,
            @PathVariable Long boardId,
            @PathVariable Long commentId
    ) {
        return ResponseEntity.ok(commentService.update(authUser, request, boardId, commentId));
    }

    @DeleteMapping("/{commentId}")
    public void delete(
            @Auth AuthUser authUser,
            @PathVariable Long boardId,
            @PathVariable Long commentId
    ) {
        commentService.delete(authUser, boardId, commentId);
    }
}

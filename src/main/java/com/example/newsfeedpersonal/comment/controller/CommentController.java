package com.example.newsfeedpersonal.comment.controller;

import com.example.newsfeedpersonal.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
}

package com.example.newsfeedpersonal.board.controller;

import com.example.newsfeedpersonal.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


}

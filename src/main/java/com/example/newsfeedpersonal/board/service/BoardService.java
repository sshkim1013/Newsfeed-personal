package com.example.newsfeedpersonal.board.service;

import com.example.newsfeedpersonal.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;


}

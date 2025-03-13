package com.example.newsfeedpersonal.board.controller;

import com.example.newsfeedpersonal.auth.annotation.Auth;
import com.example.newsfeedpersonal.auth.entity.AuthUser;
import com.example.newsfeedpersonal.board.dto.request.BoardSaveRequest;
import com.example.newsfeedpersonal.board.dto.request.BoardUpdateRequest;
import com.example.newsfeedpersonal.board.dto.response.BoardResponse;
import com.example.newsfeedpersonal.board.dto.response.BoardSaveResponse;
import com.example.newsfeedpersonal.board.dto.response.BoardUpdateResponse;
import com.example.newsfeedpersonal.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardSaveResponse> save(
            @Auth AuthUser user,
            @RequestBody BoardSaveRequest request) {
        return new ResponseEntity<>(boardService.save(user, request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BoardResponse>> findAll(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @PageableDefault(page=0, size=10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(boardService.findAll(pageable, startDate, endDate).getContent());
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponse> findOne(@PathVariable Long boardId) {
        return ResponseEntity.ok(boardService.findOne(boardId));
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<BoardUpdateResponse> update(
            @Auth AuthUser user,
            @PathVariable Long boardId,
            @RequestBody BoardUpdateRequest request
    ) {
        return ResponseEntity.ok(boardService.update(user, boardId, request));
    }

    @DeleteMapping("/{boardId}")
    public void delete(
            @Auth AuthUser user,
            @PathVariable Long boardId
    ) {
        boardService.delete(user, boardId);
    }
}

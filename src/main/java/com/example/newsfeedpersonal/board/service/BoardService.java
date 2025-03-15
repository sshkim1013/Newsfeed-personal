package com.example.newsfeedpersonal.board.service;

import com.example.newsfeedpersonal.auth.entity.AuthUser;
import com.example.newsfeedpersonal.board.dto.request.BoardSaveRequest;
import com.example.newsfeedpersonal.board.dto.request.BoardUpdateRequest;
import com.example.newsfeedpersonal.board.dto.response.BoardResponse;
import com.example.newsfeedpersonal.board.dto.response.BoardSaveResponse;
import com.example.newsfeedpersonal.board.dto.response.BoardUpdateResponse;
import com.example.newsfeedpersonal.board.entity.Board;
import com.example.newsfeedpersonal.board.repository.BoardRepository;
import com.example.newsfeedpersonal.user.entity.User;
import com.example.newsfeedpersonal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public BoardSaveResponse save(AuthUser authUser, BoardSaveRequest request) {

        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
        );

        Board board = new Board(request.getTitle(), request.getContent(), user);
        Board savedBoard = boardRepository.save(board);

        return new BoardSaveResponse(savedBoard.getId(),
                                     savedBoard.getUser().getId(),
                                     savedBoard.getTitle(),
                                     savedBoard.getContent(),
                                     savedBoard.getLikes(),
                                     savedBoard.getCreatedAt(),
                                     savedBoard.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)
    public Page<BoardResponse> findAll(Pageable pageable, String startDate, String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;

        if (startDate != null) {
            startDateTime = LocalDate.parse(startDate, formatter).atStartOfDay();
        }
        if (endDate != null) {
            endDateTime = LocalDate.parse(endDate, formatter).atTime(LocalTime.MAX);
        }

        Page<Board> boards;

        if (startDateTime != null && endDateTime != null) {
            boards = boardRepository.findByCreatedAtBetween(startDateTime, endDateTime, pageable);
        } else if (startDateTime != null) {
            boards = boardRepository.findByCreatedAtAfter(startDateTime, pageable);
        } else if (endDateTime != null) {
            boards = boardRepository.findByCreatedAtBefore(endDateTime, pageable);
        } else {
            boards = boardRepository.findAll(pageable);
        }

        return boards.map(board -> new BoardResponse(
                        board.getId(),
                        board.getUser().getId(),
                        board.getTitle(),
                        board.getContent(),
                        board.getLikes(),
                        board.getCreatedAt(),
                        board.getUpdatedAt())
        );
    }

    @Transactional(readOnly = true)
    public BoardResponse findOne(Long boardId) {

        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글이 존재하지 않습니다.")
        );

        return new BoardResponse(board.getId(),
                                 board.getUser().getId(),
                                 board.getTitle(),
                                 board.getContent(),
                                 board.getLikes(),
                                 board.getCreatedAt(),
                                 board.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)
    public Page<BoardResponse> findFollowsBoards(AuthUser authUser, Pageable pageable) {

        User sender = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다.")
        );

        Page<Board> boards = boardRepository.findAllByFollower(sender.getId(), pageable);

        return boards.map(board -> new BoardResponse(
                board.getId(),
                board.getUser().getId(),
                board.getTitle(),
                board.getContent(),
                board.getLikes(),
                board.getCreatedAt(),
                board.getUpdatedAt())
        );
    }

    @Transactional
    public BoardUpdateResponse update(AuthUser authUser, Long boardId, BoardUpdateRequest request) {

        // 게시글이 존재하는 지 확인
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글이 존재하지 않습니다.")
        );

        //[ 예외 처리 ] 타 유저의 게시글을 수정하려고 할 때.
        if (!board.getUser().getId().equals(authUser.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인의 게시글만 수정할 수 있습니다.");
        }

        board.updateTitleAndContent(request.getTitle(), request.getContent());

        return new BoardUpdateResponse(board.getId(),
                                       board.getUser().getId(),
                                       board.getTitle(),
                                       board.getContent(),
                                       board.getLikes(),
                                       board.getCreatedAt(),
                                       board.getUpdatedAt()
        );
    }

    @Transactional
    public void delete(AuthUser authUser, Long boardId) {

        // 게시글이 존재하는 지 확인
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글이 존재하지 않습니다.")
        );

        //[ 예외 처리 ] 타 유저의 게시글을 수정하려고 할 때.
        if (!board.getUser().getId().equals(authUser.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인의 게시글만 삭제할 수 있습니다.");
        }

        boardRepository.delete(board);
    }
}

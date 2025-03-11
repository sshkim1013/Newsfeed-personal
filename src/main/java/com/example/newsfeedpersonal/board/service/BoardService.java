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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                                     savedBoard.getCreatedAt(),
                                     savedBoard.getUpdatedAt());
    }

    @Transactional(readOnly = true)
    public List<BoardResponse> findAll() {
        List<Board> boards = boardRepository.findAll();

        return boards.stream()
                .map(board -> new BoardResponse(
                        board.getId(),
                        board.getUser().getId(),
                        board.getTitle(),
                        board.getContent(),
                        board.getCreatedAt(),
                        board.getUpdatedAt())).toList();
    }

//    @Transactional(readOnly = true)
//    public BoardResponse findOne(Long boardId) {
//
//    }
//
//    @Transactional
//    public BoardUpdateResponse update(AuthUser authUser, Long boardId, BoardUpdateRequest request) {
//
//        User user = userRepository.findById(authUser.getId()).orElseThrow(
//                () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
//        );
//
//
//
//    }

    @Transactional
    public void delete(AuthUser authUser, Long boardId) {

        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
        );


    }
}

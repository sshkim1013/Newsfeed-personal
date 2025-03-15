package com.example.newsfeedpersonal.like.board.service;

import com.example.newsfeedpersonal.auth.entity.AuthUser;
import com.example.newsfeedpersonal.board.entity.Board;
import com.example.newsfeedpersonal.board.repository.BoardRepository;
import com.example.newsfeedpersonal.like.board.entity.BoardLike;
import com.example.newsfeedpersonal.like.board.repository.BoardLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardLikeService {

    private final BoardRepository boardRepository;
    private final BoardLikeRepository likeRepository;

    @Transactional
    public void like(AuthUser authUser, Long boardId) {

        //1. 게시글 찾기.
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글을 찾을 수 없습니다.")
        );

        //2. 게시글을 작성한 유저인지 검증(자기 자신이라면 게시글에 좋아요 불가능)
        if (board.getUser().getId().equals(authUser.getId())) {
            throw new IllegalStateException("자신의 게시글에는 좋아요를 표시할 수 없습니다.");
        }

        //3. 이미 좋아요가 눌러졌는지 검증.
        Optional<BoardLike> existBoardLike = likeRepository.findByUserAndBoard(board.getUser(), board);
        if (existBoardLike.isPresent()) {
            throw new IllegalStateException("이미 좋아요가 표시되었습니다.");
        }

        //4. 해당 게시글에 좋아요 저장.
        BoardLike boardLike = new BoardLike(board.getUser(), board);
        likeRepository.save(boardLike);

        //5. 해당 게시글에 좋아요 수 증가.
        board.increaseLikes();
    }

    @Transactional
    public void unlike(AuthUser authUser, Long boardId) {

        //1. 게시글 찾기.
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글을 찾을 수 없습니다.")
        );

        //2. 게시글을 작성한 유저인지 검증(자기 자신이라면 게시글에 좋아요 취소 불가능)
        if (board.getUser().getId().equals(authUser.getId())) {
            throw new IllegalStateException("자신의 게시글에는 좋아요를 표시할 수 없습니다.");
        }

        //3. 이미 좋아요가 눌러졌는지 검증.
        Optional<BoardLike> existBoardLike = likeRepository.findByUserAndBoard(board.getUser(), board);
        if (existBoardLike.isEmpty()) {
            throw new IllegalStateException("이미 좋아요가 표시되어 있지 않습니다.");
        }

        //4. 해당 게시글의 좋아요 취소.
        likeRepository.delete(existBoardLike.get());

        //5. 해당 게시글의 좋아요 수 감소.
        board.decreaseLike();
    }
}

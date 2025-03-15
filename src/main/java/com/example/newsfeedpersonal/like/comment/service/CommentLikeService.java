package com.example.newsfeedpersonal.like.comment.service;

import com.example.newsfeedpersonal.auth.entity.AuthUser;
import com.example.newsfeedpersonal.board.repository.BoardRepository;
import com.example.newsfeedpersonal.comment.entity.Comment;
import com.example.newsfeedpersonal.comment.repository.CommentRepository;
import com.example.newsfeedpersonal.like.comment.entity.CommentLike;
import com.example.newsfeedpersonal.like.comment.repository.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository likeRepository;

    @Transactional
    public void like(AuthUser authUser, Long boardId, Long commentId) {

        //1. 게시글 찾기.
        boardRepository.findById(boardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글을 찾을 수 없습니다.")
        );

        System.out.println("1");

        //2. 댓글 찾기.
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없습니다.")
        );

        System.out.println("2");

        //3. 검증 유저와 댓글 작성 유저가 같은지 검증.
        if (comment.getUser().getId().equals(authUser.getId())) {
            throw new IllegalStateException("자신이 작성한 댓글에는 좋아요를 표시할 수 없습니다.");
        }

        //4. 좋아요 상태 검증.
        Optional<CommentLike> existCommentLike = likeRepository.findByUserAndComment(comment.getUser(), comment);
        if (existCommentLike.isPresent()) {
            throw new IllegalStateException("이미 좋아요를 누르신 댓글입니다.");
        }

        //5. 댓글 좋아요 저장.
        CommentLike commentLike = new CommentLike(comment.getUser(), comment);
        likeRepository.save(commentLike);

        //6. 댓글 좋아요 수 증가.
        comment.increaseLike();

    }

    @Transactional
    public void unlike(AuthUser authUser, Long boardId, Long commentId) {

        //1. 게시글 찾기.
        boardRepository.findById(boardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글을 찾을 수 없습니다.")
        );

        //2. 댓글 찾기.
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없습니다.")
        );

        //3. 검증 유저와 댓글 작성 유저가 같은지 검증.
        if (comment.getUser().getId().equals(authUser.getId())) {
            throw new IllegalStateException("자신이 작성한 댓글에는 좋아요를 표시할 수 없습니다.");
        }

        //4. 좋아요 상태 검증.
        Optional<CommentLike> existCommentLike = likeRepository.findByUserAndComment(comment.getUser(), comment);
        if (existCommentLike.isEmpty()) {
            throw new IllegalStateException("이미 좋아요가 취소된 댓글입니다.");
        }

        //5. 댓글 좋아요 취소.
        likeRepository.delete(existCommentLike.get());

        //6. 댓글 좋아요 수 감소.
        comment.decreaseLike();
    }
}

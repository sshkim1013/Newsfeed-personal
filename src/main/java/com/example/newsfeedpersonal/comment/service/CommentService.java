package com.example.newsfeedpersonal.comment.service;

import com.example.newsfeedpersonal.auth.entity.AuthUser;
import com.example.newsfeedpersonal.board.entity.Board;
import com.example.newsfeedpersonal.board.repository.BoardRepository;
import com.example.newsfeedpersonal.comment.dto.request.CommentSaveRequest;
import com.example.newsfeedpersonal.comment.dto.request.CommentUpdateRequest;
import com.example.newsfeedpersonal.comment.dto.response.CommentResponse;
import com.example.newsfeedpersonal.comment.dto.response.CommentSaveResponse;
import com.example.newsfeedpersonal.comment.dto.response.CommentUpdateResponse;
import com.example.newsfeedpersonal.comment.entity.Comment;
import com.example.newsfeedpersonal.comment.repository.CommentRepository;
import com.example.newsfeedpersonal.user.entity.User;
import com.example.newsfeedpersonal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentSaveResponse save(AuthUser authUser, CommentSaveRequest request, Long boardId) {

        // 인증된 유저 찾기.
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다.")
        );

        //게시글 찾기.
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글을 찾을 수 없습니다.")
        );

        Comment comment = new Comment(request.getContent(), user, board);
        Comment savedComment = commentRepository.save(comment);

        return new CommentSaveResponse(savedComment.getId(),
                                       savedComment.getUser().getId(),
                                       savedComment.getBoard().getId(), // == board.getId()
                                       savedComment.getContent(),
                                       savedComment.getCreatedAt(),
                                       savedComment.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> findAll(Long boardId) {

        //게시글 찾기.
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글을 찾을 수 없습니다.")
        );

        // 특정 게시글(boardId)에 대한 댓글만 조회.
        List<Comment> comments = commentRepository.findByBoardId(boardId);

        return comments.stream()
                .map(comment -> new CommentResponse(
                        comment.getId(),
                        comment.getUser().getId(),
                        comment.getBoard().getId(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getUpdatedAt()
                ))
                .toList();
    }

    @Transactional
    public CommentUpdateResponse update(AuthUser authUser, CommentUpdateRequest request, Long boardId, Long commentId) {

        //게시글 존재 여부 검증.
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글을 찾을 수 없습니다.")
        );

        // 댓글 찾기 (해당 게시글에 속한 댓글인지 검증)
        Comment comment = commentRepository.findByIdAndBoardId(commentId, boardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글에 존재하지 않는 댓글입니다.")
        );

        //댓글을 작성한 유저가 맞는지 검증.
        if (!authUser.getId().equals(board.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인이 작성한 댓글만 수정할 수 있습니다.");
        }

        //댓글 수정.
        comment.updateContent(request.getNewContent());

        return new CommentUpdateResponse(comment.getId(),
                                         comment.getUser().getId(),
                                         comment.getBoard().getId(),
                                         comment.getContent(),
                                         comment.getCreatedAt(),
                                         comment.getUpdatedAt()
        );
    }

    @Transactional
    public void delete(AuthUser authUser, Long boardId, Long commentId) {

        //게시글 존재 여부 검증.
        boardRepository.findById(boardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글을 찾을 수 없습니다.")
        );

        // 댓글 찾기 (해당 게시글에 속한 댓글인지 검증)
        Comment comment = commentRepository.findByIdAndBoardId(commentId, boardId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글에 존재하지 않는 댓글입니다.")
        );

        //댓글을 작성한 유저가 맞는지 검증.
        if (!authUser.getId().equals(comment.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "본인이 작성한 댓글만 삭제할 수 있습니다.");
        }

        //댓글 삭제.
        commentRepository.delete(comment);
    }
}

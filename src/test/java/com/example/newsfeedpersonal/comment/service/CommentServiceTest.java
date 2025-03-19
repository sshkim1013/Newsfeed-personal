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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    private User user;
    private Board board;
    private Comment comment;
    private AuthUser authUser;

    @BeforeEach
    void setUp() {
        user = new User("sshkim1013@naver.com", "김수환", "@Sh12345");
        ReflectionTestUtils.setField(user, "id", 1L);

        board = new Board("게시글 제목", "게시글 내용", user);
        ReflectionTestUtils.setField(board, "id", 1L);

        comment = new Comment("댓글 내용", user, board);
        ReflectionTestUtils.setField(comment, "id", 1L);

        authUser = new AuthUser(user.getId(), user.getEmail(), user.getName());
    }

    @Test
    void 댓글_저장_성공() {

        // given
        CommentSaveRequest request = new CommentSaveRequest("댓글 내용");

        when(userRepository.findById(authUser.getId())).thenReturn(Optional.of(user));
        when(boardRepository.findById(board.getId())).thenReturn(Optional.of(board));
        when(commentRepository.save(any(Comment.class))).thenReturn(new Comment(request.getContent(), user, board));

        // when
        CommentSaveResponse response = commentService.save(authUser, request, board.getId());

        // then
        assertNotNull(response);
        assertEquals(response.getContent(), request.getContent());
        assertEquals(response.getUserId(), user.getId());
        assertEquals(response.getBoardId(), board.getId());
    }

    @Test
    void 댓글_저장_실패_존재하지_않는_유저() {

        // given
        CommentSaveRequest request = new CommentSaveRequest("댓글 내용");
        when(userRepository.findById(authUser.getId())).thenReturn(Optional.empty());

        // when & then
        assertThrows(ResponseStatusException.class, () -> commentService.save(authUser, request, board.getId()));
    }

    @Test
    void 댓글_저장_실패_존재하지_않는_게시글() {

        //given
        CommentSaveRequest request = new CommentSaveRequest("댓글 내용");
        lenient().when(boardRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(ResponseStatusException.class, () -> commentService.save(authUser, request, board.getId()));
    }

    @Test
    void 댓글_전체_조회() {

        when(boardRepository.findById(board.getId())).thenReturn(Optional.of(board));
        when(commentRepository.findByBoardId(board.getId())).thenReturn(List.of(
                new Comment("댓글1", user, board),
                new Comment("댓글2", user, board)
        ));

        List<CommentResponse> comments = commentService.findAll(board.getId());

        assertEquals(comments.size(), 2);
    }

    @Test
    void 댓글_조회_실패_존재하지_않는_게시글() {
        when(boardRepository.findById(board.getId())).thenReturn(java.util.Optional.empty());

        assertThrows(ResponseStatusException.class, () -> commentService.findAll(board.getId()));
    }

    @Test
    void 댓글_조회_실패_존재하지_않는_댓글() {
        lenient().when(commentRepository.findByIdAndBoardId(comment.getId(), 100L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> commentService.findAll(board.getId()));
    }

    @Test
    void 댓글_수정_성공() {

        // given
        CommentUpdateRequest request = new CommentUpdateRequest("수정된 댓글 내용");

        when(boardRepository.findById(board.getId())).thenReturn(Optional.of(board));
        when(commentRepository.findByIdAndBoardId(comment.getId(), board.getId())).thenReturn(Optional.of(comment));

        // when
        CommentUpdateResponse response = commentService.update(authUser, request, board.getId(), comment.getId());

        assertNotNull(response);
        assertEquals(response.getContent(), request.getNewContent());
        assertEquals(response.getUserId(), user.getId());
        assertEquals(response.getBoardId(), board.getId());
    }

    @Test
    void 댓글_삭제_성공() {

        // given
        when(boardRepository.findById(board.getId())).thenReturn(Optional.of(board));
        when(commentRepository.findByIdAndBoardId(comment.getId(), board.getId())).thenReturn(Optional.of(comment));

        // when
        commentService.delete(authUser, board.getId(), comment.getId());

        // then
        verify(commentRepository, times(1)).delete(comment);
    }

}
package com.example.newsfeedpersonal.board.service;

import com.example.newsfeedpersonal.auth.entity.AuthUser;
import com.example.newsfeedpersonal.board.dto.request.BoardUpdateRequest;
import com.example.newsfeedpersonal.board.dto.response.BoardResponse;
import com.example.newsfeedpersonal.board.dto.response.BoardUpdateResponse;
import com.example.newsfeedpersonal.board.entity.Board;
import com.example.newsfeedpersonal.board.repository.BoardRepository;
import com.example.newsfeedpersonal.config.PasswordEncoder;
import com.example.newsfeedpersonal.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    @Mock
    private PasswordEncoder encoder;

    private AuthUser authUser;

    @Test
    void 게시글_조회_성공() {

        // given
        Long boardId = 1L;
        User user = new User("sshkim1013@naver.com", "김수환", "Sh097982*");
        Board board = new Board("제목", "내용", user);
        ReflectionTestUtils.setField(board, "id", boardId);

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));

        // when
        BoardResponse boardResponse = boardService.findOne(boardId);

        // then
        assertThat(boardResponse).isNotNull();
        assertThat(boardResponse.getId()).isEqualTo(boardId);
        assertThat(boardResponse.getTitle()).isEqualTo(board.getTitle());
        assertThat(boardResponse.getContent()).isEqualTo(board.getContent());
        assertThat(boardResponse.getUserId()).isEqualTo(user.getId());
    }

    @Test
    void 게시글_조회_실패_존재하지_않음() {
        // given
        Long boardId = 1L;
        User user = new User("sshkim1013@naver.com", "김수환", "Sh097982*");
        Board board = new Board("제목", "내용", user);
        ReflectionTestUtils.setField(board, "id", 100L);

        when(boardRepository.findById(boardId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(ResponseStatusException.class, () -> boardService.findOne(boardId));
    }

    @Test
    void 게시글_수정_성공() {

        // given
        User user = new User("sshkim1013@naver.com", "김수환", "Sh097982*");
        ReflectionTestUtils.setField(user, "id", 1L);

        Long boardId = 1L;
        Board board = new Board("제목", "내용", user);
        ReflectionTestUtils.setField(board, "id", boardId);

        AuthUser authUser = new AuthUser(user.getId(), user.getEmail(), user.getName());

        BoardUpdateRequest request = new BoardUpdateRequest("수정된 제목", "수정된 내용");

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));

        // when
        BoardUpdateResponse response = boardService.update(authUser, boardId, request);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo(request.getTitle());
        assertThat(response.getContent()).isEqualTo(request.getContent());
    }

    @Test
    void 게시글_수정_실퍠_다른_사용자() {
        // given
        User user = new User("sshkim1013@naver.com", "김수환", "Sh097982*");
        ReflectionTestUtils.setField(user, "id", 1L);

        Long boardId = 1L;
        Board board = new Board("제목", "내용", user);
        ReflectionTestUtils.setField(board, "id", boardId);

        AuthUser authUser = new AuthUser(2L, user.getEmail(), user.getName());

        BoardUpdateRequest request = new BoardUpdateRequest("수정된 제목", "수정된 내용");

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));

        // when & then
        assertThrows(ResponseStatusException.class, () -> boardService.update(authUser, boardId, request));
    }

    @Test
    void 게시글_수정_실패_게시글_존재하지_않음() {
        // given
        Long boardId = 999L; // 존재하지 않는 게시글 ID
        AuthUser authUser = new AuthUser(1L, "email", "name"); // 인증된 사용자

        // when & then
        assertThrows(ResponseStatusException.class, () -> boardService.update(authUser, boardId, new BoardUpdateRequest("제목", "내용")));
    }

    @Test
    void 게시글_삭제_성공() {
        // given
        User user = new User("sshkim1013@naver.com", "김수환", "Sh097982*");
        ReflectionTestUtils.setField(user, "id", 1L);

        Long boardId = 1L;
        Board board = new Board("제목", "내용", user);
        ReflectionTestUtils.setField(board, "id", boardId);

        AuthUser authUser = new AuthUser(user.getId(), user.getEmail(), user.getName());

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));

        // when
        boardService.delete(authUser, boardId);

        // then
        verify(boardRepository, times(1)).delete(board);

    }

    @Test
    void 게시글_삭제_실패_다른_사용자() {
        // given
        User user = new User("sshkim1013@naver.com", "김수환", "Sh097982*");
        ReflectionTestUtils.setField(user, "id", 1L);

        Long boardId = 1L;
        Board board = new Board("제목", "내용", user);
        ReflectionTestUtils.setField(board, "id", boardId);

        AuthUser authUser = new AuthUser(2L, user.getEmail(), user.getName());

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));

        assertThrows(ResponseStatusException.class, () -> boardService.delete(authUser, boardId));
    }

    @Test
    void 게시글_삭제_실패_게시글_존재하지_않음() {
        // given
        Long boardId = 999L; // 존재하지 않는 게시글 ID
        AuthUser authUser = new AuthUser(1L, "email", "name"); // 인증된 사용자

        // when & then
        assertThrows(ResponseStatusException.class, () -> boardService.delete(authUser, boardId));
    }
}
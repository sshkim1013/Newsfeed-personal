package com.example.newsfeedpersonal.board.controller;

import com.example.newsfeedpersonal.auth.entity.AuthUser;
import com.example.newsfeedpersonal.board.dto.request.BoardSaveRequest;
import com.example.newsfeedpersonal.board.dto.request.BoardUpdateRequest;
import com.example.newsfeedpersonal.board.dto.response.BoardResponse;
import com.example.newsfeedpersonal.board.dto.response.BoardSaveResponse;
import com.example.newsfeedpersonal.board.dto.response.BoardUpdateResponse;
import com.example.newsfeedpersonal.board.service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoardController.class)
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BoardService boardService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 게시글_저장() throws Exception {

        //given
        BoardSaveRequest request = new BoardSaveRequest();
        ReflectionTestUtils.setField(request, "title", "제목");
        ReflectionTestUtils.setField(request, "content", "내용");

        BoardSaveResponse response = new BoardSaveResponse(1L, 2L, "제목", "내용", 0,
                                                            LocalDateTime.now(), LocalDateTime.now());

        given(boardService.save(any(AuthUser.class), any(BoardSaveRequest.class)))
                .willReturn(response);

        // then
        mockMvc.perform(post("/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isCreated());
    }

    @Test
    void 게시글_단건_조회() throws Exception {

        // given
        Long boardId = 1L;
        BoardResponse response = new BoardResponse(boardId, 1L, "제목", "내용", 0,
                LocalDateTime.now(), LocalDateTime.now());

        given(boardService.findOne(boardId)).willReturn(response);

        // when & then
        mockMvc.perform(get("/boards/{boardId}", boardId))
                .andExpect(status().isOk())  // HTTP 상태 코드 200 OK 확인
                .andExpect(jsonPath("$.id").value(boardId))  // id 값 확인
                .andExpect(jsonPath("$.title").value("제목"))  // 제목 확인
                .andExpect(jsonPath("$.content").value("내용"))  // 내용 확인
                .andExpect(jsonPath("$.likes").value(0))  // 좋아요 수 확인
                .andExpect(jsonPath("$.createdAt").exists())  // createdAt 확인
                .andExpect(jsonPath("$.updatedAt").exists());  // updatedAt 확인
    }

    @Test
    void 게시글_수정_성공() throws Exception {
        // given
        Long boardId = 1L;
        BoardUpdateRequest request = new BoardUpdateRequest("새로운 제목", "새로운 내용");
        BoardUpdateResponse response = new BoardUpdateResponse(boardId, 1L, "새로운 제목", "새로운 내용", 0,
                LocalDateTime.now(), LocalDateTime.now());

        given(boardService.update(any(AuthUser.class), eq(boardId), any(BoardUpdateRequest.class)))
                .willReturn(response);

        // when & then
        mockMvc.perform(put("/boards/{boardId}", boardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())  // HTTP 상태 코드 200 OK 확인
                .andExpect(jsonPath("$.id").value(boardId))  // id 확인
                .andExpect(jsonPath("$.title").value("새로운 제목"))
                .andExpect(jsonPath("$.content").value("새로운 내용"))
                .andExpect(jsonPath("$.likes").value(0))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void 게시글_삭제_성공() throws Exception {
        // given
        Long boardId = 1L;

        doNothing().when(boardService).delete(any(AuthUser.class), eq(boardId));

        // when & then
        mockMvc.perform(delete("/boards/{boardId}", boardId))
                .andExpect(status().isOk());
    }

}
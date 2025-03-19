package com.example.newsfeedpersonal.follow.controller;

import com.example.newsfeedpersonal.auth.entity.AuthUser;
import com.example.newsfeedpersonal.follow.service.FollowService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FollowController.class)
class FollowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FollowService followService;

    @Autowired
    private ObjectMapper objectMapper;

    private final Long targetUserId = 1L;
    private final AuthUser authUser = new AuthUser(targetUserId, "sshkim1013@naver.com", "김수환");

    @Test
    void 팔로우_요청_처리() throws Exception {
        doNothing().when(followService).follow(Mockito.any(AuthUser.class), Mockito.eq(targetUserId));

        mockMvc.perform(post("/follows/{targetUserId}", targetUserId)
                        .requestAttr("authUser", authUser))
                .andExpect(status().isOk());
    }

    @Test
    void 언팔로우_요청_처리() throws Exception {
        doNothing().when(followService).follow(Mockito.any(AuthUser.class), Mockito.eq(targetUserId));

        mockMvc.perform(delete("/follows/{targetUserId}", targetUserId)
                        .requestAttr("authUser", authUser))
                .andExpect(status().isOk());
    }

}


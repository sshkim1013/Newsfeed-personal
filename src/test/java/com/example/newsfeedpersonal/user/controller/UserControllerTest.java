package com.example.newsfeedpersonal.user.controller;

import com.example.newsfeedpersonal.auth.entity.AuthUser;
import com.example.newsfeedpersonal.user.dto.request.UserUpdatePasswordRequest;
import com.example.newsfeedpersonal.user.dto.request.UserUpdateRequest;
import com.example.newsfeedpersonal.user.dto.response.UserResponse;
import com.example.newsfeedpersonal.user.dto.response.UserUpdateResponse;
import com.example.newsfeedpersonal.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private final Long userId = 1L;
    private final AuthUser authUser = new AuthUser(userId, "sshkim1013@naver.com", "김수환");

    @Test
    void 사용자_조회_성공() throws Exception {

        //given
        UserResponse response = new UserResponse(userId, authUser.getEmail(), authUser.getName(), 0, 0);
        given(userService.findUser(eq(userId), any(AuthUser.class))).willReturn(response);

        // when & then
        mockMvc.perform(get("/users/{userId}", userId)
                        .requestAttr("authUser", authUser))
                        .andExpect(jsonPath("$.id").value(userId))
                        .andExpect(jsonPath("$.name").value("김수환"))
                        .andExpect(jsonPath("$.email").value("sshkim1013@naver.com"))
                        .andExpect(status().isOk());
    }

    @Test
    void 사용자_프로필_수정_성공() throws Exception {

        // given
        UserUpdateRequest request = new UserUpdateRequest("newUser@naver.com", "새로운 유저");
        UserUpdateResponse response = new UserUpdateResponse(userId, request.getEmail(), request.getName(), 0, 0, LocalDateTime.now());

        given(userService.updateProfile(eq(userId), any(AuthUser.class), any(UserUpdateRequest.class)))
                .willReturn(response);

        // when & then
        mockMvc.perform(put("/users/{userId}/profile", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .requestAttr("authUser", authUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value("새로운 유저"));
    }

    @Test
    void 사용자_비밀번호_수정_성공() throws Exception {
        UserUpdatePasswordRequest request = new UserUpdatePasswordRequest("@Sh12345", "@Freedom99");
        willDoNothing().given(userService).updatePassword(eq(userId), any(AuthUser.class), any(UserUpdatePasswordRequest.class));

        mockMvc.perform(put("/users/{userId}/password", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .requestAttr("authUser", authUser))
                .andExpect(status().isOk());
    }

}

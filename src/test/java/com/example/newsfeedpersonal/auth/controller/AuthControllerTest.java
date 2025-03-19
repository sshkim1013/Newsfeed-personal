package com.example.newsfeedpersonal.auth.controller;

import com.example.newsfeedpersonal.auth.dto.request.SigninRequest;
import com.example.newsfeedpersonal.auth.dto.request.SignupRequest;
import com.example.newsfeedpersonal.auth.dto.response.SigninResponse;
import com.example.newsfeedpersonal.auth.dto.response.SignupResponse;
import com.example.newsfeedpersonal.auth.service.AuthService;
import com.example.newsfeedpersonal.user.dto.request.WithdrawRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    private SignupRequest signupRequest;
    private SignupResponse signupResponse;
    private SigninRequest signinRequest;
    private SigninResponse signinResponse;
    private WithdrawRequest withdrawRequest;

    @Test
    void 회원가입_성공() throws Exception {

        // given
        signupRequest = new SignupRequest("sshkim1013@naver.com", "김수환", "@Sh12345");
        signupResponse = new SignupResponse("token");

        given(authService.signup(any(SignupRequest.class))).willReturn(signupResponse);

        // when & then
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").value("token"));
    }

    @Test
    void 로그인_성공() throws Exception {
        // given
        signinRequest = new SigninRequest("sshkim1013@naver.com", "@Sh12345");
        signinResponse = new SigninResponse("token");

        given(authService.signin(any(SigninRequest.class))).willReturn(signinResponse);

        // when & then
        mockMvc.perform(post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signinRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").value("token"));
    }

    @Test
    void 로그인_실패_잘못된_정보() throws Exception {

        // given
        SigninRequest request = new SigninRequest("sshkim1013@naver.com", "@WrongPassword123");

        given(authService.signin(any(SigninRequest.class))).willReturn(signinResponse);

        // when & then
        mockMvc.perform(post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void 회원탈퇴_성공() throws Exception {

        //given
        withdrawRequest = new WithdrawRequest("sshkim1013@naver.com", "@Sh12345");
        doNothing().when(authService).withdraw(any(WithdrawRequest.class));

        // when & then
        mockMvc.perform(delete("/auth/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(withdrawRequest)))
                .andExpect(status().isOk());

    }
}
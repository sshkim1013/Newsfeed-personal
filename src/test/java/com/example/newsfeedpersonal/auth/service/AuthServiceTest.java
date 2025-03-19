package com.example.newsfeedpersonal.auth.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.newsfeedpersonal.auth.dto.request.SigninRequest;
import com.example.newsfeedpersonal.auth.dto.request.SignupRequest;
import com.example.newsfeedpersonal.auth.dto.response.SigninResponse;
import com.example.newsfeedpersonal.auth.dto.response.SignupResponse;
import com.example.newsfeedpersonal.config.JwtUtil;
import com.example.newsfeedpersonal.config.PasswordEncoder;
import com.example.newsfeedpersonal.user.dto.request.WithdrawRequest;
import com.example.newsfeedpersonal.user.entity.User;
import com.example.newsfeedpersonal.user.repository.UserRepository;
import com.sun.jdi.request.InvalidRequestStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    private SignupRequest signupRequest;
    private SigninRequest signinRequest;
    private WithdrawRequest withdrawRequest;
    private User user;

    @BeforeEach
    void setUp() {
        signupRequest = new SignupRequest("sshkim1013@naver.com", "김수환", "@Sh12345");
        signinRequest = new SigninRequest(signupRequest.getEmail(), signupRequest.getPassword());
        withdrawRequest = new WithdrawRequest(signinRequest.getEmail(), signinRequest.getPassword());
        user = new User(signupRequest.getEmail(), signupRequest.getName(), "encodedPassword");
    }

    @Test
    void 회원가입_성공() {
        // given
        given(userRepository.existsByEmail(signupRequest.getEmail())).willReturn(false);
        given(passwordEncoder.encode(signupRequest.getPassword())).willReturn("encodedPassword");
        given(userRepository.save(any(User.class))).willReturn(user);

        doReturn("Bearer token").when(jwtUtil).createToken(any(), anyString(), anyString());
        doReturn("token").when(jwtUtil).substringToken("Bearer token");

        // when
        SignupResponse response = authService.signup(signupRequest);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getJwt()).isEqualTo("token");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void 회원가입_실패_이미_존재하는_이메일() {
        // given
        given(userRepository.existsByEmail(signupRequest.getEmail())).willReturn(true);

        // when & then
        assertThrows(InvalidRequestStateException.class, () -> authService.signup(signupRequest));
    }

    @Test
    void 로그인_성공() {
        // given
        String email = "sshkim1013@naver.com";
        String name = "김수환";
        String password = "@Sh12345";
        String encodedPassword = "encoded@Sh12345";
        String jwtToken = "Bearer jwtToken";
        String extractedToken = "jwtToken";

        User user = new User(email, name, encodedPassword);

        SigninRequest signinRequest = new SigninRequest(email, password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwtUtil.createToken(user.getId(), user.getEmail(), user.getName())).thenReturn(jwtToken);
        when(jwtUtil.substringToken(jwtToken)).thenReturn(extractedToken);

        // when
        SigninResponse response = authService.signin(signinRequest);

        // then
        assertEquals(extractedToken, response.getJwt());
        Mockito.verify(userRepository).findByEmail(email);
        Mockito.verify(passwordEncoder).matches(password, encodedPassword);
        Mockito.verify(jwtUtil).createToken(user.getId(), user.getEmail(), user.getName());
        Mockito.verify(jwtUtil).substringToken(jwtToken);
    }

    @Test
    void 로그인_실패_존재하지_않는_회원() {
        given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> authService.signin(signinRequest));
    }

    @Test
    void 로그인_실패_비밀번호_불일치() {

        //given
        given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())).willReturn(false);

        //when & then
        assertThrows(ResponseStatusException.class, () -> authService.signin(signinRequest));
    }

    @Test
    void 회원탈퇴_성공() {

        // given
        given(userRepository.findByEmail(withdrawRequest.getEmail())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(withdrawRequest.getPassword(), user.getPassword())).willReturn(true);
        doNothing().when(userRepository).delete(user);

        // when
        authService.withdraw(withdrawRequest);

        // then
        verify(userRepository).delete(user);
    }

    @Test
    void 회원탈퇴_실패_회원_없음() {
        given(userRepository.findByEmail(withdrawRequest.getEmail())).willReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> authService.withdraw(withdrawRequest));
    }

    @Test
    void 회원탈퇴_실패_비밀번호_불일치() {

        // given
        given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(withdrawRequest.getPassword(), user.getPassword())).willReturn(false);

        // when & then
        assertThrows(ResponseStatusException.class, () -> authService.withdraw(withdrawRequest));
    }

}

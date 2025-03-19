package com.example.newsfeedpersonal.user.service;

import com.example.newsfeedpersonal.auth.entity.AuthUser;
import com.example.newsfeedpersonal.config.PasswordEncoder;
import com.example.newsfeedpersonal.user.dto.request.UserUpdatePasswordRequest;
import com.example.newsfeedpersonal.user.dto.request.UserUpdateRequest;
import com.example.newsfeedpersonal.user.dto.response.UserResponse;
import com.example.newsfeedpersonal.user.dto.response.UserUpdateResponse;
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

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private AuthUser authUser;

    @BeforeEach
    void setUp() {
        String password = "@Sh12345";
        String encodedPassword = encoder.encode(password);
        user = new User("sshkim1013@naver.com", "김수환", encodedPassword);
        ReflectionTestUtils.setField(user, "id", 1L);
        authUser = new AuthUser(user.getId(), user.getEmail(), user.getName());
    }

    @Test
    void 유저_조회_성공_본인일_경우() {
        // given
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // when
        UserResponse response = userService.findUser(user.getId(), authUser);

        // then
        assertThat(response.getId()).isEqualTo(user.getId());
        assertThat(response.getEmail()).isEqualTo(user.getEmail());  // 본인일 경우 이메일 포함
        verify(userRepository).findById(user.getId());
    }

    @Test
    void 프로필_수정_성공() {

        //given
        UserUpdateRequest request = new UserUpdateRequest("FreedomSh@naver.com", "김수환");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // when
        UserUpdateResponse response = userService.updateProfile(user.getId(), authUser, request);

        // then
        assertThat(response.getEmail()).isEqualTo(request.getEmail());
        assertThat(response.getName()).isEqualTo(request.getName());
    }

    @Test
    void 프로필_수정_실패_권한없음() {

        // given
        AuthUser authUser = new AuthUser(999L, "other@naver.com", "다른 유저");  // 다른 사용자
        UserUpdateRequest request = new UserUpdateRequest("newEmail@naver.com", "새이름");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // when & then
        assertThatThrownBy(() -> userService.updateProfile(user.getId(), authUser, request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("프로필 수정 권한이 없습니다.");
    }

    @Test
    void 비밀번호_수정_성공() {
        //given
        UserUpdatePasswordRequest request = new UserUpdatePasswordRequest(user.getPassword(), "@Freedom99");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(encoder.matches(request.getOldPassword(), user.getPassword())).thenReturn(true);
        when(encoder.encode(request.getNewPassword())).thenReturn("@Freedom99");

        //when
        userService.updatePassword(user.getId(), authUser, request);

        //then
        verify(userRepository).findById(user.getId());  //유저가 정상적으로 호출되었는가?
        verify(encoder).encode(request.getNewPassword());   //바뀐 비밀번호가 정상적으로 암호화 되었는가?
    }

    @Test
    void 비밀번호_수정_실패_기존비밀번호_틀림() {
        //given
        UserUpdatePasswordRequest request = new UserUpdatePasswordRequest("hello12345*", "@Freedom99");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(encoder.matches(request.getOldPassword(), user.getPassword())).thenReturn(false);

        //when & then
        assertThatThrownBy(() -> userService.updatePassword(user.getId(), authUser, request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("기존의 비밀번호가 아닙니다.");

    }


}
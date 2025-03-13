package com.example.newsfeedpersonal.user.service;

import com.example.newsfeedpersonal.auth.entity.AuthUser;
import com.example.newsfeedpersonal.config.PasswordEncoder;
import com.example.newsfeedpersonal.user.dto.request.UserUpdatePasswordRequest;
import com.example.newsfeedpersonal.user.dto.request.UserUpdateRequest;
import com.example.newsfeedpersonal.user.dto.response.UserResponse;
import com.example.newsfeedpersonal.user.dto.response.UserUpdateResponse;
import com.example.newsfeedpersonal.user.entity.User;
import com.example.newsfeedpersonal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserResponse findUser(Long userId, AuthUser authUser) {
        //유저 존재 여부 확인.
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.")
        );

        //인증 유저 본인이 아니면 민감한 정보(email) 반환 X
        if (!authUser.getId().equals(user.getId())) {
            return new UserResponse(user.getId(), user.getName());
        }

        return new UserResponse(user.getId(), user.getEmail(), user.getName());
    }

    @Transactional
    public UserUpdateResponse updateProfile(Long userId, AuthUser authUser, UserUpdateRequest request) {
        //유저 존재 여부 확인.
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.")
        );

        //본인의 프로필만 수정 가능.
        if (!authUser.getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "해당 유저에 대한 프로필 수정 권한이 없습니다.");
        }

        //이미 존재하는 이메일.
        if (user.getEmail().equals(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다.");
        }

        //유저의 프로필 정보(이메일, 이름) 수정.
        user.updateProfile(request.getEmail(), request.getName());

        //변경된 유저의 정보 반환.
        return new UserUpdateResponse(user.getId(),
                                      user.getEmail(),
                                      user.getName(),
                                      user.getUpdatedAt()
        );
    }

    @Transactional
    public void updatePassword(Long userId, AuthUser authUser, UserUpdatePasswordRequest request) {
        //유저 존재 여부 확인.
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다.")
        );

        //본인의 비밀번호만 수정 가능.
        if (!authUser.getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "해당 유저에 대한 비밀번호 수정 권한이 없습니다.");
        }

        //기존의 비밀번호와 같은 경우에만 수정 가능.
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "기존의 비밀번호가 아닙니다.");
        }

        //기존의 비밀번호와 새 비밀번호가 같은 경우.
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "기존의 비밀번호와 다르게 입력해 주세요.");
        }

        //비밀번호 변경.
        String encodedPassword = passwordEncoder.encode(request.getNewPassword());  //비밀번호 변경 후, 인코딩!!
        user.updatePassword(encodedPassword);
    }
}

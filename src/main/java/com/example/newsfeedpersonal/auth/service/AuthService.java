package com.example.newsfeedpersonal.auth.service;

import com.example.newsfeedpersonal.auth.dto.request.SigninRequest;
import com.example.newsfeedpersonal.auth.dto.request.SignupRequest;
import com.example.newsfeedpersonal.auth.dto.response.SigninResponse;
import com.example.newsfeedpersonal.auth.dto.response.SignupResponse;
import com.example.newsfeedpersonal.config.JwtUtil;
import com.example.newsfeedpersonal.config.PasswordEncoder;
import com.example.newsfeedpersonal.user.entity.User;
import com.example.newsfeedpersonal.user.repository.UserRepository;
import com.sun.jdi.request.InvalidRequestStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public SignupResponse signup(SignupRequest signupRequest) {

        //같은 이메일의 회원이 존재하는 지 검증.
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new InvalidRequestStateException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        User user = new User(signupRequest.getEmail(), signupRequest.getName(), encodedPassword);
        User savedUser = userRepository.save(user);

        String bearerJwt = jwtUtil.createToken(savedUser.getId(), savedUser.getEmail(), savedUser.getName());
        String jwt = jwtUtil.substringToken(bearerJwt);

        return new SignupResponse(jwt);
    }

    @Transactional(readOnly = true)
    public SigninResponse signin(SigninRequest signinRequest) {

        User user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "회원의 정보를 찾을 수 없습니다.")
        );

        // 로그인 시 이메일과 비밀번호가 일치하지 않을 경우 401을 반환합니다.
        if (!passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "올바르지 않은 비밀번호입니다.");
        }

        String bearerJwt = jwtUtil.createToken(user.getId(), user.getEmail(), user.getName());
        String jwt = jwtUtil.substringToken(bearerJwt);

        return new SigninResponse(jwt);
    }
}

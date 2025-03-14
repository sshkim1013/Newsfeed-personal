package com.example.newsfeedpersonal.follow.service;

import com.example.newsfeedpersonal.auth.entity.AuthUser;
import com.example.newsfeedpersonal.follow.entity.Follow;
import com.example.newsfeedpersonal.follow.repository.FollowRepository;
import com.example.newsfeedpersonal.user.entity.User;
import com.example.newsfeedpersonal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public void follow(AuthUser authUser, Long targetUserId) {

        //팔로우 요청하는 유저의 존재 여부 검증
        User sender = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        );

        //팔로우 당하는 유저의 존재 여부 검증
        User receiver = userRepository.findById(targetUserId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        );

        //자기 자신을 팔로우하려는 경우
        if (sender.getId().equals(receiver.getId())) {
            throw new IllegalStateException("본인을 팔로우할 수 없습니다.");
        }

        Follow follow = new Follow(sender, receiver);
        followRepository.save(follow);
    }

    public void unfollow(AuthUser authUser, Long targetUserId) {
        //팔로우 요청하는 유저의 존재 여부 검증
        User sender = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        );

        //팔로우 당하는 유저의 존재 여부 검증
        User receiver = userRepository.findById(targetUserId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        );

        //팔로워와 팔로잉을 통한 Follow 정보 찾기.
        Follow follow = followRepository.findBySenderAndReceiver(sender, receiver).orElseThrow(
                () -> new IllegalStateException("해당 유저들의 팔로우 정보가 존재하지 않습니다.")
        );

        followRepository.delete(follow);
    }
}

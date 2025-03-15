package com.example.newsfeedpersonal.follow.service;

import com.example.newsfeedpersonal.auth.entity.AuthUser;
import com.example.newsfeedpersonal.follow.entity.Follow;
import com.example.newsfeedpersonal.follow.repository.FollowRepository;
import com.example.newsfeedpersonal.user.entity.User;
import com.example.newsfeedpersonal.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Transactional
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

        //팔로우 상태 검증.
        Optional<Follow> existFollowState = followRepository.findBySenderAndReceiver(sender, receiver);
        if (existFollowState.isPresent()) {
            throw new IllegalStateException("이미 팔로우된 상태입니다.");
        }

        //팔로우.
        Follow follow = new Follow(sender, receiver);
        followRepository.save(follow);

        //각각의 팔로워 팔로잉 수 증가.
        sender.increaseFollowingUsers();
        receiver.increaseFollowerUsers();
    }

    @Transactional
    public void unfollow(AuthUser authUser, Long targetUserId) {
        //팔로우 요청하는 유저의 존재 여부 검증
        User sender = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        );

        //팔로우 당하는 유저의 존재 여부 검증
        User receiver = userRepository.findById(targetUserId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        );

        //언팔로우 상태 검증.
        Optional<Follow> follow = followRepository.findBySenderAndReceiver(sender, receiver);
        if (follow.isEmpty()) {
            throw new IllegalStateException("이미 언팔로우된 상태입니다.");
        }

        //언팔로우.
        followRepository.delete(follow.get());

        //각각의 팔로워 팔로잉 수 감소.
        sender.decreaseFollowingUsers();
        receiver.decreaseFollowerUsers();
    }
}

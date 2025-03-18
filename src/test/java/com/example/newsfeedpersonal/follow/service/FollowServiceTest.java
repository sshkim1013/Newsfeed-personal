package com.example.newsfeedpersonal.follow.service;

import com.example.newsfeedpersonal.auth.entity.AuthUser;
import com.example.newsfeedpersonal.follow.entity.Follow;
import com.example.newsfeedpersonal.follow.repository.FollowRepository;
import com.example.newsfeedpersonal.user.entity.User;
import com.example.newsfeedpersonal.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    @Mock
    private FollowRepository followRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FollowService followService;

    @Test
    void 팔로우_성공() {

        User sender = new User("sender@naver.com", "팔로워", "Sender99@");
        User receiver = new User("receiver@naver.com", "팔로잉", "Receiver99@");
        ReflectionTestUtils.setField(sender, "id", 1L);
        ReflectionTestUtils.setField(receiver, "id", 2L);

        AuthUser authUser = new AuthUser(sender.getId(), sender.getEmail(), sender.getName());

        when(userRepository.findById(sender.getId())).thenReturn(Optional.of(sender));
        when(userRepository.findById(receiver.getId())).thenReturn(Optional.of(receiver));
        when(followRepository.findBySenderAndReceiver(sender, receiver)).thenReturn(Optional.empty());

        followService.follow(authUser, receiver.getId());

        verify(followRepository, times(1)).save(any(Follow.class));
        assertEquals(1, sender.getFollowingUsers());
        assertEquals(1, receiver.getFollowerUsers());
    }

    @Test
    void 팔로우_실패_자기자신을_팔로우() {
        User sender = new User("sender@naver.com", "팔로워", "Sender99@");
        User receiver = new User("receiver@naver.com", "팔로잉", "Receiver99@");
        ReflectionTestUtils.setField(sender, "id", 1L);
        ReflectionTestUtils.setField(receiver, "id", 2L);

        AuthUser authUser = new AuthUser(sender.getId(), sender.getEmail(), sender.getName());

        when(userRepository.findById(sender.getId())).thenReturn(Optional.of(sender));

        assertThrows(IllegalStateException.class, () -> followService.follow(authUser, sender.getId()));
    }

    @Test
    void 팔로우_실패_이미_팔로우_상태() {

        User sender = new User("sender@naver.com", "팔로워", "Sender99@");
        User receiver = new User("receiver@naver.com", "팔로잉", "Receiver99@");
        ReflectionTestUtils.setField(sender, "id", 1L);
        ReflectionTestUtils.setField(receiver, "id", 2L);

        AuthUser authUser = new AuthUser(sender.getId(), sender.getEmail(), sender.getName());

        when(userRepository.findById(sender.getId())).thenReturn(Optional.of(sender));
        when(userRepository.findById(receiver.getId())).thenReturn(Optional.of(receiver));
        when(followRepository.findBySenderAndReceiver(sender, receiver)).thenReturn(Optional.of(new Follow(sender, receiver)));

        assertThrows(IllegalStateException.class, () -> followService.follow(authUser, receiver.getId()));
    }

    @Test
    void 언팔로우_성공() {

        User sender = new User("sender@naver.com", "팔로워", "Sender99@");
        User receiver = new User("receiver@naver.com", "팔로잉", "Receiver99@");
        ReflectionTestUtils.setField(sender, "id", 1L);
        ReflectionTestUtils.setField(receiver, "id", 2L);

        AuthUser authUser = new AuthUser(sender.getId(), sender.getEmail(), sender.getName());

        Follow follow = new Follow(sender, receiver);
        when(userRepository.findById(sender.getId())).thenReturn(Optional.of(sender));
        when(userRepository.findById(receiver.getId())).thenReturn(Optional.of(receiver));
        when(followRepository.findBySenderAndReceiver(sender, receiver)).thenReturn(Optional.of(follow));

        followService.unfollow(authUser, receiver.getId());

        verify(followRepository, times(1)).delete(follow);
        assertEquals(0, sender.getFollowingUsers());
        assertEquals(0, receiver.getFollowerUsers());
    }

    @Test
    void 언팔로우_실패_이미_언팔로우_상태() {

        User sender = new User("sender@naver.com", "팔로워", "Sender99@");
        User receiver = new User("receiver@naver.com", "팔로잉", "Receiver99@");
        ReflectionTestUtils.setField(sender, "id", 1L);
        ReflectionTestUtils.setField(receiver, "id", 2L);

        AuthUser authUser = new AuthUser(sender.getId(), sender.getEmail(), sender.getName());

        when(userRepository.findById(sender.getId())).thenReturn(Optional.of(sender));
        when(userRepository.findById(receiver.getId())).thenReturn(Optional.of(receiver));
        when(followRepository.findBySenderAndReceiver(sender, receiver)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> followService.unfollow(authUser, receiver.getId()));
    }

}
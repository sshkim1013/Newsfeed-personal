package com.example.newsfeedpersonal.follow.repository;

import com.example.newsfeedpersonal.follow.entity.Follow;
import com.example.newsfeedpersonal.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findBySenderAndReceiver(User sender, User receiver);
}

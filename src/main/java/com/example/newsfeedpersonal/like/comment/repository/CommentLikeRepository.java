package com.example.newsfeedpersonal.like.comment.repository;

import com.example.newsfeedpersonal.comment.entity.Comment;
import com.example.newsfeedpersonal.like.comment.entity.CommentLike;
import com.example.newsfeedpersonal.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);
}

package com.example.newsfeedpersonal.comment.repository;

import com.example.newsfeedpersonal.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

package com.example.newsfeedpersonal.comment.repository;

import com.example.newsfeedpersonal.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBoardId(Long boardId);

    Optional<Comment> findByIdAndBoardId(Long commentId, Long boardId);
}

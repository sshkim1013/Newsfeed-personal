package com.example.newsfeedpersonal.like.board.repository;

import com.example.newsfeedpersonal.board.entity.Board;
import com.example.newsfeedpersonal.like.board.entity.BoardLike;
import com.example.newsfeedpersonal.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByUserAndBoard(User user, Board board);
}

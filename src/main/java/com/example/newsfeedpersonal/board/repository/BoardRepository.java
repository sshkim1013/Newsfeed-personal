package com.example.newsfeedpersonal.board.repository;

import com.example.newsfeedpersonal.board.entity.Board;
import com.example.newsfeedpersonal.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Page<Board> findByCreatedAtAfter(LocalDateTime startDate, Pageable pageable);
    Page<Board> findByCreatedAtBefore(LocalDateTime endDate, Pageable pageable);

    @Query("SELECT b FROM Board b JOIN Follow f ON b.user.id = f.receiver.id WHERE f.sender.id = :userId")
    Page<Board> findAllByFollower(@Param("userId") Long userId, Pageable pageable);

}

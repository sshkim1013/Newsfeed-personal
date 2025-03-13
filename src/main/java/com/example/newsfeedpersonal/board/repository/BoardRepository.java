package com.example.newsfeedpersonal.board.repository;

import com.example.newsfeedpersonal.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Page<Board> findByCreatedAtAfter(LocalDateTime startDate, Pageable pageable);
    Page<Board> findByCreatedAtBefore(LocalDateTime endDate, Pageable pageable);
}

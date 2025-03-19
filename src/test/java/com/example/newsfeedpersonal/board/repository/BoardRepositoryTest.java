package com.example.newsfeedpersonal.board.repository;

import com.example.newsfeedpersonal.board.entity.Board;
import com.example.newsfeedpersonal.follow.entity.Follow;
import com.example.newsfeedpersonal.follow.repository.FollowRepository;
import com.example.newsfeedpersonal.user.entity.User;
import com.example.newsfeedpersonal.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    private User user;
    private Board board1, board2, board3;

    @BeforeEach
    void setUp() {
        user = userRepository.save(new User("sshkim1013@naver.com", "김수환", "@Sh12345"));

        board1 = boardRepository.save(new Board("제목1", "내용1", user, LocalDateTime.now().minusDays(2)));
        board2 = boardRepository.save(new Board("제목2", "내용2", user, LocalDateTime.now().minusDays(1)));
        board3 = boardRepository.save(new Board("제목3", "내용3", user, LocalDateTime.now()));
    }

    @Test
    void 특정_기간_내_생성된_게시글_조회() {

        // given
        LocalDateTime startDate = LocalDateTime.now().minusDays(3);
        LocalDateTime endDate = LocalDateTime.now();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Board> result = boardRepository.findByCreatedAtBetween(startDate, endDate, pageable);

        // then
        assertThat(result.getContent()).hasSize(3);
    }

    @Test
    void 특정_날짜_이전에_생성된_게시글_조회() {
        // given
        LocalDateTime endDate = LocalDateTime.now();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Board> result = boardRepository.findByCreatedAtBefore(endDate, pageable);

        // then
        assertThat(result.getContent()).hasSize(3);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("제목1");
    }

    @Test
    void 특정_날짜_이후에_생성된_게시글_조회() {
        // given
        LocalDateTime startDate = LocalDateTime.now().minusDays(3);
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Board> result = boardRepository.findByCreatedAtAfter(startDate, pageable);

        // then
        assertThat(result.getContent()).hasSize(3);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("제목1");
    }

    @Test
    void 팔로워의_게시글_조회_성공() {
        // given
        User sender = userRepository.save(new User("kimsuhwan99@gmail.com", "김수환", "@Sh12345"));
        User receiver = userRepository.save(new User("Freedom@naver.com", "프리덤", "@Freedom99"));

        Follow follow = new Follow(sender, receiver);
        followRepository.save(follow);

        boardRepository.save(new Board("게시글 1", "내용 1", receiver));
        boardRepository.save(new Board("게시글 2", "내용 2", receiver));

        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Board> result = boardRepository.findAllByFollower(sender.getId(), pageable);

        // then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("게시글 1");
        assertThat(result.getContent().get(1).getTitle()).isEqualTo("게시글 2");
    }

}
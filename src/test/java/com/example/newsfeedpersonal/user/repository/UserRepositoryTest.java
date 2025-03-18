package com.example.newsfeedpersonal.user.repository;

import com.example.newsfeedpersonal.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void 이메일로_회원을_찾을_수_있다() {

        // given
        String email = "sshkim1013@naver.com";
        User user = new User(email, "김수환", "Sh097982*");
        userRepository.save(user);

        // when
        User foundUser = userRepository.findByEmail(email).orElse(null);

        // then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo(email);
    }

    @Test
    void 존재하지_않는_이메일로_조회하면_Optional_반환() {

        // given & when
        User foundUser = userRepository.findByEmail("abc@naver.com").orElse(null);

        // then
        assertThat(foundUser).isNull();
    }

    @Test
    void 이메일이_존재하는지_확인할_수_있다() {

        // given
        String email = "sshkim1013@naver.com";
        User user = new User(email, "김수환", "Sh097982*");
        userRepository.save(user);

        // then
        boolean exists = userRepository.existsByEmail(email);

        // then
        assertThat(exists).isTrue();
    }

    @Test
    void 존재하지_않는_이메일로_조회_시_false를_반환한다() {

        // given
        String email = "sshkim1013@naver.com";
        User user = new User(email, "김수환", "Sh097982*");
        userRepository.save(user);

        //given & when
        boolean exists = userRepository.existsByEmail("abc@naver.com");

        // then
        assertThat(exists).isFalse();
    }
}
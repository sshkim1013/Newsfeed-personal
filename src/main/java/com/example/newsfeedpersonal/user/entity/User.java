package com.example.newsfeedpersonal.user.entity;

import com.example.newsfeedpersonal.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int followerUsers = 0;

    @Column(nullable = false)
    private int followingUsers = 0;

    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    //유저 프로필 수정 메서드
    public void updateProfile(String email, String name) {
        this.email = email;
        this.name = name;
    }

    //유저 비밀번호 수정 메서드
    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void increaseFollowerUsers() {
        this.followerUsers++;
    }

    public void increaseFollowingUsers() {
        this.followingUsers++;
    }

    public void decreaseFollowerUsers() {
        if (followerUsers > 0) {
            this.followerUsers--;
        }
    }

    public void decreaseFollowingUsers() {
        if (followingUsers > 0) {
            this.followingUsers--;
        }
    }

}

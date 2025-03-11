package com.example.newsfeedpersonal.like.post;

import com.example.newsfeedpersonal.board.entity.Board;
import com.example.newsfeedpersonal.common.BaseEntity;
import com.example.newsfeedpersonal.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "board_likes")
public class BoardLike extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(nullable = false)
    private boolean likeStatus;

    @PrePersist
    public void prePersist() {
        this.likeStatus = false;     // likeBoard 필드의 기본값을 false 처리.
    }
}

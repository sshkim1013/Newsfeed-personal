package com.example.newsfeedpersonal.like.comment;

import com.example.newsfeedpersonal.comment.entity.Comment;
import com.example.newsfeedpersonal.common.BaseEntity;
import com.example.newsfeedpersonal.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comment_likes")
public class CommentLike extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Column(nullable = false)
    private boolean likeStatus;

    @PrePersist
    public void prePersist() {
        this.likeStatus = false;     // likeComment 필드의 기본값을 false 처리.
    }
}

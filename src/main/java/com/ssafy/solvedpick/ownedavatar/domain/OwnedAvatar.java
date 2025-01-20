package com.ssafy.solvedpick.ownedavatar.domain;


import com.ssafy.solvedpick.avatars.domain.Avatar;
import com.ssafy.solvedpick.members.domain.Member;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Table(name = "owned_avatars")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnedAvatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avatar_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Avatar avatar;

    @Builder.Default
    @Column(nullable = false)
    private boolean visible = false;

    @Builder.Default
    @Column(nullable = false)
    private boolean sold = false;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public void updateVisibility() {
        this.visible = !this.visible;
    }

}
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
    @Column(name = "owned_avatar_id", columnDefinition = "INT UNSIGNED")
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

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime acquiredAt;

    public void updateVisibility() {
        this.visible = !this.visible;
    }

}
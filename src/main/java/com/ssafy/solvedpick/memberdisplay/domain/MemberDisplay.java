package com.ssafy.solvedpick.memberdisplay.domain;

import com.ssafy.solvedpick.members.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_display")
public class MemberDisplay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "Integer unsigned")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    private Member member;

    @Column(columnDefinition = "tinyint")
    private int tier;

    @Builder.Default
    private boolean tierVisible = false;

    @Column(columnDefinition = "Integer unsigned")
    private int solvedCount;

    @Builder.Default
    private boolean solvedVisible = false;

    @Column(columnDefinition = "Integer unsigned")
    private int streak;

    @Builder.Default
    private boolean streakVisible = false;

    private String title;

    @Builder.Default
    private boolean titleVisible = false;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}

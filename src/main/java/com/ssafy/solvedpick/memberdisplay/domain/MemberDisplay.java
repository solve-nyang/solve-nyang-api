package com.ssafy.solvedpick.memberdisplay.domain;

import com.ssafy.solvedpick.api.dto.UserData;
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
    private Integer tier;

    @Builder.Default
    private Boolean tierVisible = false;

    @Column(columnDefinition = "tinyint")
    private Integer memberClass;

    @Builder.Default
    private Boolean memberClassVisible = false;

    @Column(columnDefinition = "Integer unsigned")
    private Integer solvedCount;

    @Builder.Default
    private Boolean solvedVisible = false;

    @Column(columnDefinition = "Integer unsigned")
    private Integer streak;

    @Builder.Default
    private Boolean streakVisible = false;

    private String title;

    @Builder.Default
    private Boolean titleVisible = false;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public static MemberDisplay initMemberDisplay(Member member) {
        return MemberDisplay.builder()
                .member(member)
                .build();
    }

    public void updateInfo(UserData userData) {
        this.tier = userData.getTier();
        this.memberClass = userData.getMemberClass();
        this.solvedCount = userData.getSolvedCount();
        this.streak = userData.getMaxStreak();
    }
}

package com.ssafy.solvedpick.members.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.solvedpick.ownedbackgrounds.domain.OwnedBackground;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ssafy.solvedpick.ownedavatar.domain.OwnedAvatar;
import com.ssafy.solvedpick.problem.domain.Problem;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "Integer unsigned")
    private Long id;
	
    @Column(nullable = false, unique = true, length = 30)
    private String username;
    
    @Column(nullable = false, length = 72)
    private String password;
    
    @Builder.Default
    private boolean verified = false;
    
    @Builder.Default
    private Long point = 5000L;

    @Builder.Default
    @Column(columnDefinition = "tinyint")
    private int tier = 0;

    @Builder.Default
    @Column(columnDefinition = "Integer unsigned")
    private int solvedCount = 0;

    @Builder.Default
    @Column(columnDefinition = "Integer unsigned")
    private int streak = 0;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Builder.Default
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Problem solvedProblems = null;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OwnedAvatar> avatars = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OwnedBackground> backgrounds = new ArrayList<>();

    public void addPoint(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("포인트는 음수일 수 없습니다");
        }

        this.point += amount;
    }

    public void usePoint(long point) {
    	this.point -= point;
    }
    
    public void updatePassword(String encodedNewPassword) {
    	this.password = encodedNewPassword;
    }

    public void updateInfo(int tier, int solvedCount, int streak) {
        this.tier = tier;
        this.solvedCount = solvedCount;
        this.streak = streak;
    }

    public void initSolvedProblem(Problem problem) {
        this.solvedProblems = problem;
    }
}

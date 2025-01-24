package com.ssafy.solvedpick.members.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private int point = 3000;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Builder.Default
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Problem solvedProblems = null;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OwnedAvatar> avatars = new ArrayList<>();

    public void addPoint(int amount) {
        if (amount <0) {
            throw new IllegalArgumentException("포인트는 음수일 수 없습니다");
        }
        this.point += amount;
    }

    public void initSolvedProblems() {
    	this.solvedProblems = Problem.builder()
    			.member(this)
    			.build();
    }

    public void updatePoint(int newPoint) {
        this.point += newPoint;
    }
    
    public void usePoint(int point) {
    	this.point -= point;
    }
    
}

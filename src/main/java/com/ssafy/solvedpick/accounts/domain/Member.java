package com.ssafy.solvedpick.accounts.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ssafy.solvedpick.ownedavatar.domain.OwnedAvatar;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
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
    @Column(columnDefinition = "boolean default false")
    private boolean verified = false;
    
    @Builder.Default
    @Column(columnDefinition = "Integer unsigned default 0")
    private Integer point = 0;
    
    @Builder.Default
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OwnedAvatar> avatars = new ArrayList<>();
}

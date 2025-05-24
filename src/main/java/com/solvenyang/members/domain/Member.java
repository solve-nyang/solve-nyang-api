package com.solvenyang.members.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.solvenyang.attendance.domain.AttendanceRecord;
import com.solvenyang.image.domain.Image;
import com.solvenyang.ownedbackgrounds.domain.OwnedBackground;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.solvenyang.ownedavatar.domain.OwnedAvatar;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Slf4j
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

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OwnedAvatar> avatars = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OwnedBackground> backgrounds = new ArrayList<>();
    
    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttendanceRecord> attendanceRecords = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

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

}

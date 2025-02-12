package com.ssafy.solvedpick.attendance.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "attendance_records")
public class AttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "Integer unsigned")
    private Long id;

    @Column(name = "user_id", nullable = false, columnDefinition = "Integer unsigned")
    private Long userId; 

    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public AttendanceRecord(Long userId) {
        this.userId = userId;
        this.attendanceDate = LocalDate.now();
    }

    public static AttendanceRecord create(Long userId) {
        return new AttendanceRecord(userId);
    }
} 
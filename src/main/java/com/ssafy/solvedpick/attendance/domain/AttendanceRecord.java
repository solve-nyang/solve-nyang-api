package com.ssafy.solvedpick.attendance.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ssafy.solvedpick.members.domain.Member;

import java.time.LocalDate;
import java.time.YearMonth;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Member member;

    @Column(name = "attendance_month", length = 7, nullable = false)
    private String attendanceMonth;

    @Column(name = "attendance_days", columnDefinition = "BIT(32)")
    private int attendanceDays;

    @Column(name = "continious_attendance")
    private int continiousAttendance;

    private AttendanceRecord(Member member) {
        this.member = member;
        this.attendanceMonth = YearMonth.from(LocalDate.now()).toString();
        // attendanceDays 초기값 설정 필요
    }

    public static AttendanceRecord create(Member member) {
        return new AttendanceRecord(member);
    }
}

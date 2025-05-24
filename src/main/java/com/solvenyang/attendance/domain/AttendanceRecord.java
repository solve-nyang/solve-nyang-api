package com.solvenyang.attendance.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.solvenyang.members.domain.Member;

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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Member member;

    @Column(name = "attendance_month", length = 7, nullable = false)
    private String attendanceMonth;

    @Column(name = "attendance_days", columnDefinition = "BIT(32)")
    private int attendanceDays;

    @Column(name = "continious_attendance", columnDefinition = "INT DEFAULT 1")
    private int continiousAttendance;

    private AttendanceRecord(Member member, int continiousAttendance) {
        this.member = member;
        this.attendanceMonth = YearMonth.from(LocalDate.now()).toString();
        int zeroBasedDay = LocalDate.now().getDayOfMonth() - 1;
        this.attendanceDays =  1 << zeroBasedDay;
        this.continiousAttendance = continiousAttendance;
    }

    public static AttendanceRecord create(Member member, int continiousAttendance) {
        return new AttendanceRecord(member, continiousAttendance);
    }

    public void updateAttendance (int updatedAttendance, boolean isAttendYesterday) {
        this.attendanceDays = updatedAttendance;
        if (isAttendYesterday) {
            this.continiousAttendance = (this.continiousAttendance + 1) % 7;
        } else this.continiousAttendance = 1;
    }

    public void resetContinious () {
        this.continiousAttendance = 0;
    }
}

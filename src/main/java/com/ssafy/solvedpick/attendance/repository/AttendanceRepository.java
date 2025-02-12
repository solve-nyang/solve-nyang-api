package com.ssafy.solvedpick.attendance.repository;

import com.ssafy.solvedpick.attendance.domain.AttendanceRecord;
import com.ssafy.solvedpick.members.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceRecord, Long> {
    boolean existsByMemberAndAttendanceDateBetween(Member member, LocalDate start, LocalDate end);
    boolean existsByMemberAndAttendanceDateEquals(Member member, LocalDate date);

    List<AttendanceRecord> findByMemberAndAttendanceDateBetweenOrderByAttendanceDateDesc(
        Member member, LocalDate start, LocalDate end);
    
    @Query("""
        SELECT a.attendanceDate
        FROM AttendanceRecord a
        WHERE a.member = :member
        BETWEEN :startDate AND :endDate
        ORDER BY a.attendanceDate DESC
    """)
    List<LocalDate> findContinuousAttendanceDates(
        @Param("member") Member member,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
);
}

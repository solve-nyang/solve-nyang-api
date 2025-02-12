package com.ssafy.solvedpick.attendance.repository;

import com.ssafy.solvedpick.attendance.domain.AttendanceRecord;
import com.ssafy.solvedpick.members.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceRecord, Long> {
    boolean existsByMemberAndAttendanceDateBetween(Member member, LocalDate start, LocalDate end);
    
    List<AttendanceRecord> findByMemberAndAttendanceDateBetweenOrderByAttendanceDateDesc(
        Member member, LocalDate start, LocalDate end);
} 
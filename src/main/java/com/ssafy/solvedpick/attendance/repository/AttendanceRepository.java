package com.ssafy.solvedpick.attendance.repository;

import com.ssafy.solvedpick.attendance.domain.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceRecord, Integer> {
    boolean existsByUserIdAndAttendanceDateBetween(Long userId, LocalDate start, LocalDate end);
    
    List<AttendanceRecord> findByUserIdAndAttendanceDateBetweenOrderByAttendanceDateDesc(
            Long userId, LocalDate start, LocalDate end);
} 
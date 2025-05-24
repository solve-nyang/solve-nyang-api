package com.solvenyang.attendance.repository;

import com.solvenyang.attendance.domain.AttendanceRecord;
import com.solvenyang.members.domain.Member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceRecord, Long> {
    @Query("""
        SELECT ar.attendanceDays
        FROM AttendanceRecord ar
        WHERE ar.member = :member
        AND ar.attendanceMonth = :yearMonth
    """)
    Optional<Integer> findAttendanceDaysByMemberAndMonth(@Param("member") Member member, 
                                                @Param("yearMonth") String yearMonth);

    @Query("""
    SELECT EXISTS
    (SELECT 1 FROM AttendanceRecord ar
    WHERE ar.member = :member
    AND ar.attendanceMonth = :yearMonth)
    """)
    boolean existsByMemberAndMonth(@Param("member") Member member,
                                @Param("yearMonth") String yearMonth);
    
    Optional<AttendanceRecord> findByMemberAndAttendanceMonth(Member member, String yearMonth);
}

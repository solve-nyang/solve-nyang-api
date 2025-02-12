package com.ssafy.solvedpick.attendance.service;

import com.ssafy.solvedpick.attendance.domain.AttendanceRecord;
import com.ssafy.solvedpick.attendance.repository.AttendanceRepository;
import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.memberdisplay.domain.MemberDisplay;
import com.ssafy.solvedpick.facade.UserFacade;
import com.ssafy.solvedpick.attendance.exception.AttendanceException;
import com.ssafy.solvedpick.attendance.exception.AttendanceErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceService {
    private static final int ATTENDANCE_POINT = 100;
    private static final int STREAK_BONUS = 300;
    private static final int STREAK_DAYS = 7;

    private final AttendanceRepository attendanceRepository;
    private final AuthService authService;
    private final UserFacade userFacade;

    public void checkAttendance() {
        Member member = authService.getCurrentMember();
        MemberDisplay display = member.getMemberDisplay();
        int previousSolvedCount = display.getSolvedCount();
        
      
        userFacade.syncUserInfo(member);
        
        
        if (display.getSolvedCount() <= previousSolvedCount) {
            throw new AttendanceException(AttendanceErrorCode.NO_NEW_SOLVED_PROBLEM);
        }

        LocalDate today = LocalDate.now();
        
      
        if (attendanceRepository.existsByUserIdAndAttendanceDateBetween(
                member, today, today)) {
            throw new AttendanceException(AttendanceErrorCode.ALREADY_ATTENDED);
        }

       
        AttendanceRecord record = AttendanceRecord.create(member);
        attendanceRepository.save(record);

       
        boolean hasStreak = checkStreak(member);
        int totalPoint = ATTENDANCE_POINT + (hasStreak ? STREAK_BONUS : 0);

    
        member.addPoint(totalPoint);
    }


    private boolean checkStreak(Member member) {
        LocalDate today = LocalDate.now();
        LocalDate weekAgo = today.minusDays(STREAK_DAYS - 1);
        
        List<AttendanceRecord> records = attendanceRepository
                .findByUserIdAndAttendanceDateBetweenOrderByAttendanceDateDesc(
                        member, weekAgo, today);

        return records.size() == STREAK_DAYS;
    }
} 
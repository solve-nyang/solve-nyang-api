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
        
      
        if (attendanceRepository.existsByMemberAndAttendanceDateBetween(
                member, today, today)) {
            throw new AttendanceException(AttendanceErrorCode.ALREADY_ATTENDED);
        }

       
        AttendanceRecord record = AttendanceRecord.create(member);
        attendanceRepository.save(record);

       
        boolean hasStreak = checkStreak(member);
        int totalPoint = ATTENDANCE_POINT + (hasStreak ? STREAK_BONUS : 0);

    
        member.addPoint(totalPoint);
    }


    public String countWeeklyAttendance() {
        Member member = authService.getCurrentMember();
        
        LocalDate today = LocalDate.now();
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDate twoWeeksAgo = today.minusDays(14);
        
        List<LocalDate> twoWeeksAttendance =  attendanceRepository.findContinuousAttendanceDates(member, twoWeeksAgo, yesterday);
        int continuousDays = checkContinuousAttendance(twoWeeksAttendance) % STREAK_DAYS;
        
        if (attendanceRepository.existsByMemberAndAttendanceDateEquals(member, today)) {
            continuousDays += 1;
            if (continuousDays >= 5) {
                return "고지가 눈앞입니다! 내일도 꼭 문제를 풀어봐요!";
            } else if (continuousDays >= 3) {
                return "오늘도 하루 추가! 잘하셨어요!";
            } else if (continuousDays == 2) {
                return "연속 출석을 달성했습니다!";
            }
            return "연속 출석을 달성해봅시다!";
        }
        
        if (continuousDays >= 4) {
            return "당신은 성실왕! 기다리고있었어요!";
        } else if (continuousDays >= 2) {
            return "잘하고있어요! 오늘도 문제를 풀어봅시다!";
        } else if (continuousDays == 1) {
            return "연속 출석을 달성해봅시다!";
        }
        return "문제를 풀어주세요!";
    }
    

    private boolean checkStreak(Member member) {
        LocalDate today = LocalDate.now();
        LocalDate weekAgo = today.minusDays(STREAK_DAYS - 1);
        
        List<AttendanceRecord> records = attendanceRepository
                .findByMemberAndAttendanceDateBetweenOrderByAttendanceDateDesc(
                        member, weekAgo, today);

        return records.size() == STREAK_DAYS;
    }

    private int checkContinuousAttendance(List<LocalDate> dates) {
        if (dates.isEmpty()) return 0;
        
        LocalDate yesterday = LocalDate.now().minusDays(1);
        
        if (!dates.get(0).equals(yesterday)) return 0;
        
        int continuousDays = 1;
        LocalDate previousDate = yesterday;
        
        for (int i = 1; i < dates.size(); i++) {
            if (previousDate.minusDays(1).equals(dates.get(i))) {
                continuousDays++;
                previousDate = dates.get(i);
            } else {
                break;
            }
        }
        
        return continuousDays;
    }
} 
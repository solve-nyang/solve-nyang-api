package com.ssafy.solvedpick.attendance.service;

import com.ssafy.solvedpick.attendance.domain.AttendanceRecord;
import com.ssafy.solvedpick.attendance.dto.HalfYearResponse;
import com.ssafy.solvedpick.attendance.repository.AttendanceRepository;
import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.common.error.exception.attendance.AttendanceException;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.memberdisplay.domain.MemberDisplay;
import com.ssafy.solvedpick.facade.UserFacade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceService {
    private static final int ATTENDANCE_POINT = 100;
    private static final int STREAK_BONUS = 300;

    private final AttendanceRepository attendanceRepository;
    private final AuthService authService;
    private final UserFacade userFacade;

    public void checkAttendance() {
        Member member = authService.getCurrentMember();
        MemberDisplay display = member.getMemberDisplay();
        int previousSolvedCount = display.getSolvedCount();
        String yearMonth = YearMonth.from(LocalDate.now()).toString();
        
        int attendanceDays = attendanceRepository.findAttendanceDaysByMemberAndMonth(member, yearMonth);
        int zeroBasedDay = LocalDate.now().getDayOfMonth() - 1;
        
        if ((attendanceDays & (1 << zeroBasedDay)) != 0) {
            throw new AttendanceException("이미 오늘 출석체크를 완료했습니다.");
        }
        
        int currentSolvedCount = userFacade.getCurrentSolvedCount(member.getUsername());
        
        // 새로운 문제를 풀었는지 확인
        if (currentSolvedCount <= previousSolvedCount) {
            throw new AttendanceException("새로 해결한 문제가 없습니다.");
        }

        if (!attendanceRepository.existsByMemberAndMonth(member, yearMonth)){
            AttendanceRecord record = AttendanceRecord.create(member);
            attendanceRepository.save(record);
            return;
        }
        AttendanceRecord attendanceRecord = attendanceRepository.findByMemberAndAttendanceMonth(member, yearMonth);
        boolean hasStreak = checkStreak(attendanceRecord);
        updateAttendance(attendanceRecord);

        int totalPoint = ATTENDANCE_POINT + (hasStreak ? STREAK_BONUS : 0);
        
        if (totalPoint != 0) member.addPoint(totalPoint);
    }


    public String countWeeklyAttendance() {
        Member member = authService.getCurrentMember();
        String yearMonth = YearMonth.from(LocalDate.now()).toString();
        
        AttendanceRecord attendanceRecord = attendanceRepository.findByMemberAndAttendanceMonth(member, yearMonth);

        int attendanceDays = attendanceRecord.getAttendanceDays();
        int zeroBasedDay = LocalDate.now().getDayOfMonth() - 1;
        int continuousDays = attendanceRecord.getContiniousAttendance();

        if ((attendanceDays & (1 << zeroBasedDay)) != 0) {
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


    public HalfYearResponse getHalfYearAttendance() {
        Member member = authService.getCurrentMember();
        LocalDate today = LocalDate.now();
        
        List<Map> attendances = new ArrayList<>();

        for (int i = 5; i >= 0; i--) {
            String yearMonth = YearMonth.from(today.minusMonths(i)).toString();
            int currentMonthData = attendanceRepository.findAttendanceDaysByMemberAndMonth(member, yearMonth);
            for (int j = 0; j < 31; j++) {
                if ((currentMonthData & (1 << j)) == 1) {
                    if (j < 9) {
                        attendances.add(Map.of("data", yearMonth + "-0" + (j + 1)));
                    } else attendances.add(Map.of("data", yearMonth + "-" + (j + 1)));
                }
            }
        }
        
        return HalfYearResponse.of(attendances);
    }


    @Transactional
    private void updateAttendance(AttendanceRecord attendanceRecord) {
        int attendanceDays = attendanceRecord.getAttendanceDays();
        int zeroBasedDay = LocalDate.now().getDayOfMonth() - 1;

        int newAttendanceDays = attendanceDays | (1 << zeroBasedDay);
        
        attendanceRecord.updateAttendance(newAttendanceDays);
    }

    private boolean checkStreak(AttendanceRecord attendanceRecord) {
        int continiousAttendance = attendanceRecord.getContiniousAttendance();
        return (continiousAttendance == 6);
    }
}

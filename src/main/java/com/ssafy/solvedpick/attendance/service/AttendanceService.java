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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
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
        
        int attendanceDays = attendanceRepository.findAttendanceDaysByMemberAndMonth(member, yearMonth)
                .orElse(0);
        int zeroBasedDay = LocalDate.now().getDayOfMonth() - 1;
        
//        if ((attendanceDays & (1 << zeroBasedDay)) != 0) {
//            throw new AttendanceException("이미 오늘 출석체크를 완료했습니다.");
//        }
        
        int currentSolvedCount = userFacade.getCurrentSolvedCount(member.getUsername());
        
        // 새로운 문제를 풀었는지 확인
//        if (currentSolvedCount <= previousSolvedCount) {
//            throw new AttendanceException("새로 해결한 문제가 없습니다.");
//        }
        
        if (!attendanceRepository.existsByMemberAndMonth(member, yearMonth)) {
            if (zeroBasedDay != 0) {
                AttendanceRecord record = AttendanceRecord.create(member, 1);
                attendanceRepository.save(record);

            } else {
                String lastMonth = YearMonth.from(LocalDate.now().minusMonths(1)).toString();
                AttendanceRecord lastMonthRecord = attendanceRepository.findByMemberAndAttendanceMonth(member, lastMonth)
                        .orElse(null);
                        
                int continiousAttendance = 1;
                if (lastMonthRecord != null && lastMonthRecord.getContiniousAttendance() != 0) {
                    YearMonth lastYearMonth = YearMonth.from(LocalDate.now().minusMonths(1));
                    int lastDayOfMonth = lastYearMonth.lengthOfMonth() - 1;
                    int lastAttendanceDays = lastMonthRecord.getAttendanceDays();
                    int lastContinious = lastMonthRecord.getContiniousAttendance();
                    
                    for (int i = 0; i < lastContinious && lastDayOfMonth >= 0; i++) {
                        if ((lastAttendanceDays & (1 << lastDayOfMonth)) != 0) {
                            continiousAttendance++;
                            lastDayOfMonth--;
                        } else {
                            break;
                        }
                    }
                    AttendanceRecord newRecord = AttendanceRecord.create(member, continiousAttendance);
                    attendanceRepository.save(newRecord);
                }
            }
        }

        AttendanceRecord attendanceRecord = attendanceRepository.findByMemberAndAttendanceMonth(member, yearMonth)
                .orElseThrow(() -> new AttendanceException("이번 달에 출석하지 않았습니다."));
        
        int newAttendanceDays = attendanceRecord.getAttendanceDays();
        int yesterday = zeroBasedDay - 1;
        if ((newAttendanceDays & (1 << yesterday)) == 0) {
            attendanceRecord.resetContinious();
        }

        boolean hasStreak = checkStreak(attendanceRecord);
        updateAttendance(attendanceRecord);

        int totalPoint = ATTENDANCE_POINT + (hasStreak ? STREAK_BONUS : 0);
        log.info("member add point:{}", totalPoint);
        member.addPoint(totalPoint);
    }

    public String countWeeklyAttendance() {
        Member member = authService.getCurrentMember();
        String yearMonth = YearMonth.from(LocalDate.now()).toString();
        
        AttendanceRecord attendanceRecord = attendanceRepository.findByMemberAndAttendanceMonth(member, yearMonth)
                .orElse(null);
        if (attendanceRecord == null) {
            return "문제를 풀어주세요!";
        }
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

        int yesterday = zeroBasedDay - 1;
        if ((attendanceDays & (1 << yesterday)) == 0) {
            attendanceRecord.resetContinious();
        }

        if (continuousDays >= 4) {
            return "당신은 성실왕! 기다리고 있었어요!";
        } else if (continuousDays >= 2) {
            return "잘하고 있어요! 오늘도 문제를 풀어봅시다!";
        } else if (continuousDays == 1) {
            return "연속 출석을 달성해 봅시다!";
        }
        return "문제를 풀어주세요!";
    }


    public HalfYearResponse getHalfYearAttendance() {
        try {
            Member member = authService.getCurrentMember();
            LocalDate today = LocalDate.now();
            
            List<Map<String, String>> attendances = new ArrayList<>();
    
            for (int i = 5; i >= 0; i--) {
                String yearMonth = YearMonth.from(today.minusMonths(i)).toString();
                Optional<Integer> monthDataOptional = attendanceRepository.findAttendanceDaysByMemberAndMonth(member, yearMonth);
                if (monthDataOptional.isEmpty()) {
                    continue;
                }
                int currentMonthData = monthDataOptional.get();

                for (int j = 0; j < 31; j++) {
                    if ((currentMonthData & (1 << j)) != 0) {
                        if (j < 9) {
                            attendances.add(Map.<String, String>of("date", yearMonth + "-0" + (j + 1)));
                        } else attendances.add(Map.of("date", yearMonth + "-" + (j + 1)));
                    }
                }
            }
            
            return HalfYearResponse.of(attendances);
            
        } catch (Exception e) {
            throw new AttendanceException("출석 기록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }


    private void updateAttendance(AttendanceRecord attendanceRecord) {
        int attendanceDays = attendanceRecord.getAttendanceDays();
        int zeroBasedDay = LocalDate.now().getDayOfMonth() - 1;
        int yesterday = zeroBasedDay - 1;

        boolean isAttendYesterday = ((attendanceDays & (1 << yesterday)) != 0);
        int newAttendanceDays = attendanceDays | (1 << zeroBasedDay);
        
        attendanceRecord.updateAttendance(newAttendanceDays, isAttendYesterday);
    }

    private boolean checkStreak(AttendanceRecord attendanceRecord) {
        int continiousAttendance = attendanceRecord.getContiniousAttendance();
        return (continiousAttendance == 6);
    }
}

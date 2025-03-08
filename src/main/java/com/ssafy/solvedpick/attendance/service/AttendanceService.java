package com.ssafy.solvedpick.attendance.service;

import com.ssafy.solvedpick.attendance.domain.AttendanceRecord;
import com.ssafy.solvedpick.attendance.dto.HalfYearResponse;
import com.ssafy.solvedpick.attendance.repository.AttendanceRepository;
import com.ssafy.solvedpick.common.error.exception.attendance.AttendanceException;
import com.ssafy.solvedpick.members.domain.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
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
    private static final ZoneId KOREA_ZONE_ID = ZoneId.of("Asia/Seoul");

    private final AttendanceRepository attendanceRepository;

    public void processAttendance(Member member) {
        String yearMonth = YearMonth.from(LocalDate.now(KOREA_ZONE_ID)).toString();
        int zeroBasedDay = LocalDate.now(KOREA_ZONE_ID).getDayOfMonth() - 1;

        int attendanceDays = attendanceRepository.findAttendanceDaysByMemberAndMonth(member, yearMonth)
                .orElse(0);

        if ((attendanceDays & (1 << zeroBasedDay)) != 0) {
            throw new AttendanceException("이미 오늘 출석체크를 완료했습니다.");
        }

        if (!attendanceRepository.existsByMemberAndMonth(member, yearMonth)) {
            createNewMonthAttendance(member);
        }

        AttendanceRecord attendanceRecord = attendanceRepository.findByMemberAndAttendanceMonth(member, yearMonth)
                .orElseThrow(() -> new AttendanceException("이번 달에 출석하지 않았습니다."));

        int yesterday = zeroBasedDay - 1;
        checkYesterday(attendanceRecord, yesterday);

        boolean hasStreak = checkStreak(attendanceRecord);
        updateAttendance(attendanceRecord);

        int totalPoint = ATTENDANCE_POINT + (hasStreak ? STREAK_BONUS : 0);
        member.addPoint(totalPoint);
    }

    private void createNewMonthAttendance(Member member) {
        int zeroBasedDay = LocalDate.now(KOREA_ZONE_ID).getDayOfMonth() - 1;

        if (zeroBasedDay != 0) {
            AttendanceRecord record = AttendanceRecord.create(member, 1);
            attendanceRepository.save(record);
        } else {
            processFirstDayOfMonth(member);
        }
    }

    private void processFirstDayOfMonth(Member member) {
        String lastMonth = YearMonth.from(LocalDate.now(KOREA_ZONE_ID).minusMonths(1)).toString();
        AttendanceRecord lastMonthRecord = attendanceRepository.findByMemberAndAttendanceMonth(member, lastMonth)
                .orElse(null);

        int continuousAttendance = 1;
        if (lastMonthRecord != null && lastMonthRecord.getContiniousAttendance() != 0) {
            continuousAttendance = calculateContinuousAttendance(lastMonthRecord);
        }

        AttendanceRecord newRecord = AttendanceRecord.create(member, continuousAttendance);
        attendanceRepository.save(newRecord);
    }

    private int calculateContinuousAttendance(AttendanceRecord lastMonthRecord) {
        YearMonth lastYearMonth = YearMonth.from(LocalDate.now(KOREA_ZONE_ID).minusMonths(1));
        int lastDayOfMonth = lastYearMonth.lengthOfMonth() - 1;
        int lastAttendanceDays = lastMonthRecord.getAttendanceDays();
        int lastContinuous = lastMonthRecord.getContiniousAttendance();

        int continuousAttendance = 1;
        for (int i = 0; i < lastContinuous && lastDayOfMonth >= 0; i++) {
            if ((lastAttendanceDays & (1 << lastDayOfMonth)) != 0) {
                continuousAttendance++;
                lastDayOfMonth--;
            } else {
                break;
            }
        }
        return continuousAttendance;
    }

    public String countWeeklyAttendance(Member member) {
        String yearMonth = YearMonth.from(LocalDate.now(KOREA_ZONE_ID)).toString();

        AttendanceRecord attendanceRecord = attendanceRepository.findByMemberAndAttendanceMonth(member, yearMonth)
                .orElse(null);
        if (attendanceRecord == null) {
            return "문제를 풀어주세요!";
        }
        int attendanceDays = attendanceRecord.getAttendanceDays();
        int zeroBasedDay = LocalDate.now(KOREA_ZONE_ID).getDayOfMonth() - 1;
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
        checkYesterday(attendanceRecord, yesterday);

        if (continuousDays >= 4) {
            return "당신은 성실왕! 기다리고 있었어요!";
        } else if (continuousDays >= 2) {
            return "잘하고 있어요! 오늘도 문제를 풀어봅시다!";
        } else if (continuousDays == 1) {
            return "연속 출석을 달성해 봅시다!";
        }
        return "문제를 풀어주세요!";
    }


    public HalfYearResponse getHalfYearAttendance(Member member) {
        try {
            LocalDate today = LocalDate.now(KOREA_ZONE_ID);

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


    public boolean checkToday(Member member) {
        String yearMonth = YearMonth.from(LocalDate.now(KOREA_ZONE_ID)).toString();
        int attendanceDays = attendanceRepository.findAttendanceDaysByMemberAndMonth(member, yearMonth)
                .orElse(0);
        int zeroBasedDay = LocalDate.now(KOREA_ZONE_ID).getDayOfMonth() - 1;
        return (attendanceDays & (1 << zeroBasedDay)) != 0;
    }


    private void updateAttendance(AttendanceRecord attendanceRecord) {
        int attendanceDays = attendanceRecord.getAttendanceDays();
        int zeroBasedDay = LocalDate.now(KOREA_ZONE_ID).getDayOfMonth() - 1;
        int yesterday = zeroBasedDay - 1;

        boolean isAttendYesterday = ((attendanceDays & (1 << yesterday)) != 0);
        int newAttendanceDays = attendanceDays | (1 << zeroBasedDay);

        attendanceRecord.updateAttendance(newAttendanceDays, isAttendYesterday);
    }

    private boolean checkStreak(AttendanceRecord attendanceRecord) {
        int continuousAttendance = attendanceRecord.getContiniousAttendance();
        return (continuousAttendance == 6);
    }

    private void checkYesterday(AttendanceRecord attendanceRecord , int yesterday) {
        int attendanceDays = attendanceRecord.getAttendanceDays();
        if ((attendanceDays & (1 << yesterday)) == 0) {
            attendanceRecord.resetContinious();
        }
    }
}
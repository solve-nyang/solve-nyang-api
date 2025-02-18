package com.ssafy.solvedpick.attendance.facade;

import com.ssafy.solvedpick.api.dto.UserInfoApiResponse;
import com.ssafy.solvedpick.api.service.ApiService;
import com.ssafy.solvedpick.attendance.dto.HalfYearResponse;
import com.ssafy.solvedpick.attendance.service.AttendanceService;
import com.ssafy.solvedpick.common.error.exception.attendance.AttendanceException;
import com.ssafy.solvedpick.memberdisplay.domain.MemberDisplay;
import com.ssafy.solvedpick.memberdisplay.service.MemberDisplayService;
import com.ssafy.solvedpick.members.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AttendanceFacade {
    private final AttendanceService attendanceService;
    private final MemberDisplayService memberDisplayService;
    private final ApiService apiService;

    @Transactional
    public void checkAttendance(Member member) {
        MemberDisplay display = memberDisplayService.findByMember(member);
        int previousSolvedCount = display.getSolvedCount();

        int currentSolvedCount = getCurrentSolvedCount(member.getUsername());
        if (currentSolvedCount <= previousSolvedCount) {
            throw new AttendanceException("새로 해결한 문제가 없습니다.");
        }

        attendanceService.processAttendance(member);
    }

    @Transactional(readOnly = true)
    public String countWeeklyAttendance(Member member) {
        return attendanceService.countWeeklyAttendance(member);
    }

    @Transactional(readOnly = true)
    public HalfYearResponse getHalfYearAttendance(Member member) {
        return attendanceService.getHalfYearAttendance(member);
    }

    @Transactional(readOnly = true)
    public boolean checkToday(Member member) {
        return attendanceService.checkToday(member);
    }


    private int getCurrentSolvedCount(String username) {
        UserInfoApiResponse response = apiService.getUserInfo(username);
        if (response.getItems() == null || response.getItems().isEmpty()) {
            throw new AttendanceException("User not found: " + username);
        }
        return response.getItems().get(0).getSolvedCount();
    }

}

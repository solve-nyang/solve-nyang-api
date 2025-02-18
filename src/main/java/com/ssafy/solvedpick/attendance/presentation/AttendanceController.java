package com.ssafy.solvedpick.attendance.presentation;

import com.ssafy.solvedpick.attendance.dto.HalfYearResponse;
import com.ssafy.solvedpick.attendance.dto.TodayAttendanceDTO;
import com.ssafy.solvedpick.attendance.facade.AttendanceFacade;
import com.ssafy.solvedpick.attendance.service.AttendanceService;
import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.common.dto.ResponseMessageDTO;

import com.ssafy.solvedpick.members.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AuthService authService;
    private final AttendanceFacade attendanceFacade;

    @PostMapping("/reward")
    public ResponseEntity<?> checkAttendance() {
        Member member = authService.getCurrentMember();
        attendanceFacade.checkAttendance(member);
        return ResponseEntity.ok()
    	        .body(ResponseMessageDTO.builder()
                        .message("success")
                        .build());
    }

    @GetMapping("/weekly-status")
    public ResponseEntity<?> weeklyAttendance() {
        Member member = authService.getCurrentMember();
        String message = attendanceFacade.countWeeklyAttendance(member);
        return ResponseEntity.ok()
        .body(ResponseMessageDTO.builder()
                .message(message)
                .build());
    }

    @GetMapping("/records")
    public ResponseEntity<?> getHalfYearAttendance() {
        Member member = authService.getCurrentMember();
        HalfYearResponse message = attendanceFacade.getHalfYearAttendance(member);
        return ResponseEntity.ok()
                .body(message);
    }
    
    @GetMapping("/today")
    public ResponseEntity<?> checkTodayAttendance() {
        Member member = authService.getCurrentMember();
        boolean isAttended = attendanceFacade.checkToday(member);
        return ResponseEntity.ok()
        .body(TodayAttendanceDTO.builder()
                .isAttended(isAttended)
                .build());
    }
}
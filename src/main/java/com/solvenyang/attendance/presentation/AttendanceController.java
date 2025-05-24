package com.solvenyang.attendance.presentation;

import com.solvenyang.attendance.dto.HalfYearResponse;
import com.solvenyang.attendance.dto.TodayAttendanceDTO;
import com.solvenyang.attendance.facade.AttendanceFacade;
import com.solvenyang.auth.service.AuthService;
import com.solvenyang.common.dto.ResponseMessageDTO;

import com.solvenyang.members.domain.Member;
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
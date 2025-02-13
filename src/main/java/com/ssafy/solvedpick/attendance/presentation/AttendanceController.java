package com.ssafy.solvedpick.attendance.presentation;

import com.ssafy.solvedpick.attendance.dto.HalfYearResponse;
import com.ssafy.solvedpick.attendance.service.AttendanceService;
import com.ssafy.solvedpick.common.dto.ResponseMessageDTO;
import com.ssafy.solvedpick.common.dto.TodayAttendanceDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/reward")
    public ResponseEntity<?> checkAttendance() {
        attendanceService.checkAttendance();
        return ResponseEntity.ok()
    	        .body(ResponseMessageDTO.builder()
                        .message("success")
                        .build());
    }

    @GetMapping("/weekly-status")
    public ResponseEntity<?> weeklyAttendance() {
        String message = attendanceService.countWeeklyAttendance();
        return ResponseEntity.ok()
        .body(ResponseMessageDTO.builder()
                .message(message)
                .build());
    }

    @GetMapping("/records")
    public ResponseEntity<?> getHalfYearAttendance() {
        HalfYearResponse message = attendanceService.getHalfYearAttendance();
        return ResponseEntity.ok()
                .body(message);
    }
    
    @GetMapping("/today")
    public ResponseEntity<?> checkTodayAttendance() {
        boolean isAttended = attendanceService.checkToday();
        return ResponseEntity.ok()
        .body(TodayAttendanceDTO.builder()
                .isAttended(isAttended)
                .build());
    }
}
package com.ssafy.solvedpick.attendance.presentation;

import com.ssafy.solvedpick.attendance.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/reward")
    public ResponseEntity<Void> checkAttendance() {
        attendanceService.checkAttendance(); 
        return ResponseEntity.ok().build(); 
    }

    @GetMapping("/weekly-status")
    public ResponseEntity<?> weeklyAttendance() {
        String message = attendanceService.countWeeklyAttendance();
        return ResponseEntity.ok()
                .body(message);
    }
}
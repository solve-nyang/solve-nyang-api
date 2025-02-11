package com.ssafy.solvedpick.attendance.presentation;

import com.ssafy.solvedpick.attendance.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/check")
    public ResponseEntity<Void> checkAttendance() {
        attendanceService.checkAttendance(); 
        return ResponseEntity.ok().build(); 
    }
}
package com.ssafy.solvedpick.attendance.dto;

import java.util.List;
import java.util.Map;

import lombok.Getter;

@Getter
public class HalfYearResponse {
    private final List<Map<String, String>> attendances;

    private HalfYearResponse(List<Map<String, String>> attendance) {
        this.attendances = attendance;
    }

    public static HalfYearResponse of(List<Map<String, String>> attendance) {
        return new HalfYearResponse(attendance);
    }
}
package com.ssafy.solvedpick.attendance.dto;

import java.util.List;
import java.util.Map;

public class HalfYearResponse {
    private final List<Map> attendance;

    private HalfYearResponse(List<Map> attendance) {
        this.attendance = attendance;
    }

    public static HalfYearResponse of(List<Map> attendance) {
        return new HalfYearResponse(attendance);
    }
}

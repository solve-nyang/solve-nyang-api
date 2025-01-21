package com.ssafy.solvedpick.common.grade;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Grade {
    S(5, 1.0),
    A(4, 4.0),
    B(3, 30.0),
    C(2, 45.0),
    D(1, 20.0);

    private final int value;
    private final double probability;

    public static Grade fromValue(int value) {
        return Arrays.stream(values())
                .filter(grade -> grade.value == value)
                .findFirst()
                .orElse(D);
    }
}

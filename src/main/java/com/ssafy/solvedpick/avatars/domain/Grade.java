package com.ssafy.solvedpick.avatars.domain;

import lombok.Getter;

@Getter
public enum Grade {
    S(5, 1.0),
    A(4, 4.0),
    B(3, 15.0),
    C(2, 30.0),
    D(1, 50.0);

    private final int value;
    private final double probability;

    Grade(int value, double probability) {
        this.value = value;
        this.probability = probability;
    }

    public static Grade fromValue(int value) {
        for (Grade grade : values()) {
            if (grade.value == value) {
                return grade;
            }
        }
        return null;
    }
}

package com.ssafy.solvedpick.common.utils.point;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Point {
    UNRATED(0, 0),
    BRONZE5(1, 2),
    BRONZE4(2, 4),
    BRONZE3(3, 6),
    BRONZE2(4, 8),
    BRONZE1(5, 10),
    SILVER5(6, 20),
    SILVER4(7, 30),
    SILVER3(8, 40),
    SILVER2(9, 50),
    SILVER1(10, 60),
    GOLD5(11, 100),
    GOLD4(12, 125),
    GOLD3(13, 150),
    GOLD2(14, 175),
    GOLD1(15, 200),
    PLATINUM5(16, 220),
    PLATINUM4(17, 250),
    PLATINUM3(18, 280),
    PLATINUM2(19, 310),
    PLATINUM1(20, 340),
    DIAMOND5(21, 500),
    DIAMOND4(22, 550),
    DIAMOND3(23, 600),
    DIAMOND2(24, 650),
    DIAMOND1(25, 700),
    RUBY5(26, 1000),
    RUBY4(27, 1000),
    RUBY3(28, 1000),
    RUBY2(29, 1000),
    RUBY1(30, 1000),
    MASTER(31, 0);

    private final int level;
    private final int point;

    public static int getPointFromLevel(int level) {
        if (level < 1 || level > 30) {
            return 0;
        }
        return values()[level].point;
    }

    public static String getPointName(int level) {
        if (level < 0 || level > 31) {
            throw new IllegalArgumentException("Invalid level: " + level + ". Level must be between 0 and 31");
        }
        return values()[level].name();
    }

    public static String getPointName(int level) {
        if (level < 0 || level > 30) {
            throw new IllegalArgumentException("Invalid level: " + level + ". Level must be between 0 and 30");
        }
        return values()[level - 1].name();
    }
}

package com.ssafy.solvedpick.common.utils.point;

import com.ssafy.solvedpick.composition.resource.SvgResources;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Tier {
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

    public static String getTierName(int level) {
        if (level < 0 || level > 31) {
            throw new IllegalArgumentException("Invalid level: " + level + ". Level must be between 0 and 31");
        }
        return values()[level].name();
    }

    public static Tier getTierfromLevel(int level) {
        if (level < 0 || level > 31) {
            return UNRATED;
        }
        return values()[level];
    }

    public String getSvgContent(SvgResources svgResources) {
        return switch (this) {
            case UNRATED -> svgResources.getUnrated();
            case BRONZE5 -> svgResources.getBronze5();
            case BRONZE4 -> svgResources.getBronze4();
            case BRONZE3 -> svgResources.getBronze3();
            case BRONZE2 -> svgResources.getBronze2();
            case BRONZE1 -> svgResources.getBronze1();
            case SILVER5 -> svgResources.getSilver5();
            case SILVER4 -> svgResources.getSilver4();
            case SILVER3 -> svgResources.getSilver3();
            case SILVER2 -> svgResources.getSilver2();
            case SILVER1 -> svgResources.getSilver1();
            case GOLD5 -> svgResources.getGold5();
            case GOLD4 -> svgResources.getGold4();
            case GOLD3 -> svgResources.getGold3();
            case GOLD2 -> svgResources.getGold2();
            case GOLD1 -> svgResources.getGold1();
            case PLATINUM5 -> svgResources.getPlatinum5();
            case PLATINUM4 -> svgResources.getPlatinum4();
            case PLATINUM3 -> svgResources.getPlatinum3();
            case PLATINUM2 -> svgResources.getPlatinum2();
            case PLATINUM1 -> svgResources.getPlatinum1();
            case DIAMOND5 -> svgResources.getDiamond5();
            case DIAMOND4 -> svgResources.getDiamond4();
            case DIAMOND3 -> svgResources.getDiamond3();
            case DIAMOND2 -> svgResources.getDiamond2();
            case DIAMOND1 -> svgResources.getDiamond1();
            case RUBY5 -> svgResources.getRuby5();
            case RUBY4 -> svgResources.getRuby4();
            case RUBY3 -> svgResources.getRuby3();
            case RUBY2 -> svgResources.getRuby2();
            case RUBY1 -> svgResources.getRuby1();
            case MASTER -> svgResources.getMaster();
        };
    };
}
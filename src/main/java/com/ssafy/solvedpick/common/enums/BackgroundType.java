package com.ssafy.solvedpick.common.enums;

import com.ssafy.solvedpick.composition.resource.SvgResources;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum BackgroundType {

    SPACE("Space", 99999),
    BEACH("Beach", 8000),
    FIELD("Field", 5000),
    OCEAN("Ocean", 8000),
    SAND("Sand", 5000),
    SNOW1("Snow1", 5000),
    SNOW2("Snow2", 5000),
    WINDOW1("Window1", 2000),
    WINDOW2("Window2", 5000),
    HEART("Heart", 2000);

    private final String name;
    private final int price;

    public String getSvgContent(SvgResources svgResources) {
        return switch (this) {
            case SPACE -> svgResources.getSpace();
            case BEACH -> svgResources.getBeach();
            case FIELD -> svgResources.getField();
            case OCEAN -> svgResources.getOcean();
            case SAND -> svgResources.getSand();
            case SNOW1 -> svgResources.getSnow1();
            case SNOW2 -> svgResources.getSnow2();
            case WINDOW1 -> svgResources.getWindow1();
            case WINDOW2 -> svgResources.getWindow2();
            case HEART -> svgResources.getHeart();
        };
    }

    public static BackgroundType fromName(String name) {
        return Arrays.stream(values())
                .filter(type -> type.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid BackgroundType: " + name));
    }

}
package com.ssafy.solvedpick.common.enums;

import com.ssafy.solvedpick.composition.resource.SvgResources;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum BackgroundType {

    BASE_FIELD("BaseField"),
    SPACE_FIELD("SpaceField"),;

    private final String name;

    public String getSvgContent(SvgResources svgResources) {
        return switch (this) {
            case BASE_FIELD -> svgResources.getBaseField();
            case SPACE_FIELD -> svgResources.getSpaceField();
        };
    }
}
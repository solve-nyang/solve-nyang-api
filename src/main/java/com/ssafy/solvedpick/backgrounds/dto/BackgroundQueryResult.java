package com.ssafy.solvedpick.backgrounds.dto;

import lombok.Getter;

@Getter
public class BackgroundQueryResult {

    private String name;
    private Boolean owned;

    public BackgroundQueryResult(String name, Boolean owned) {
        this.name = name;
        this.owned = owned;
    }
}

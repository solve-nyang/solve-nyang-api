package com.ssafy.solvedpick.backgrounds.dto;

import lombok.Getter;

@Getter
public class BackgroundQueryResult {

    private Long id;
    private String name;
    private Boolean owned;

    public BackgroundQueryResult(Long id, String name, Boolean owned) {
        this.id = id;
        this.name = name;
        this.owned = owned;
    }
}

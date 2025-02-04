package com.ssafy.solvedpick.backgrounds.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BackgroundInfo {

    private String name;
    private int price;
    private Boolean owned;

}

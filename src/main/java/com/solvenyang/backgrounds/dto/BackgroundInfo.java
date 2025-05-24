package com.solvenyang.backgrounds.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BackgroundInfo {

    private Long id;
    private String name;
    private int price;
    private Boolean owned;

}

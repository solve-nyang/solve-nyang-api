package com.ssafy.solvedpick.ownedbackgrounds.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnedBackgroundDTO {

    private Long id;
    private String name;

}

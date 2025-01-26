package com.ssafy.solvedpick.ownedavatar.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AvatarCollectionDTO {

    private String name;
    private String rarity;
}

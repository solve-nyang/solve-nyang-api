package com.ssafy.solvedpick.ownedavatar.dto;

import com.ssafy.solvedpick.common.utils.grade.Grade;
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
    private Boolean owned;

    public AvatarCollectionDTO(String name, int grade, Boolean owned) {
        this.name = name;
        this.rarity = Grade.fromValue(grade).name();
        this.owned = owned;
    }
}

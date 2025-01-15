package com.ssafy.solvedpick.ownedavatar.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnedAvatarDTO {

    private Long ownedAvatarId;
    private String name;
    private String rarity;
    private double dropRate;
    private boolean visible;
}

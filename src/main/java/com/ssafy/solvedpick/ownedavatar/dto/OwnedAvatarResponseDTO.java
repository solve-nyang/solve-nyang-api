package com.ssafy.solvedpick.ownedavatar.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnedAvatarResponseDTO {

    private List<OwnedAvatarDTO> avatars;
}

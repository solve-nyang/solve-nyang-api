package com.ssafy.solvedpick.ownedavatar.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAvatarResponseDTO {

    private List<OwnedAvatarDTO> avatars;
}

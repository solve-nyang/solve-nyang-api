package com.ssafy.solvedpick.ownedavatar.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ExtensionAvatarResponseDTO {

    List<String> avatars;
}

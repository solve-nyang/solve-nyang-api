package com.solvenyang.ownedavatar.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AvatarCollectionResponseDTO {

    private List<AvatarCollectionDTO> collections;
}

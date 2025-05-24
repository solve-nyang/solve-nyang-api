package com.solvenyang.ownedavatar.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AvatarSaleRequestDTO {

    private List<SoldAvatarDTO> soldAvatars;
}

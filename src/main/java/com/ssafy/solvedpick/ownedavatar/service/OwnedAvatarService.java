package com.ssafy.solvedpick.ownedavatar.service;

import com.ssafy.solvedpick.ownedavatar.dto.AvatarSaleRequestDto;
import com.ssafy.solvedpick.ownedavatar.dto.AvatarSaleResponseDto;
import com.ssafy.solvedpick.ownedavatar.dto.OwnedAvatarDTO;

import java.util.List;

public interface OwnedAvatarService {

    List<OwnedAvatarDTO> getOwnedAvatars(Long memberId);
    void updateAvatarVisibility(Long avatarId);
    AvatarSaleResponseDto sellAvatars(AvatarSaleRequestDto request);
}

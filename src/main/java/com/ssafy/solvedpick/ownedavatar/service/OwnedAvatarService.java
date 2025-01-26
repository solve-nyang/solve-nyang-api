package com.ssafy.solvedpick.ownedavatar.service;

import com.ssafy.solvedpick.ownedavatar.dto.*;

import java.util.List;

public interface OwnedAvatarService {

    List<OwnedAvatarDTO> getOwnedAvatars(Long memberId);

    void updateAvatarVisibility(Long avatarId);

    AvatarSaleResponseDTO sellAvatars(AvatarSaleRequestDTO request);

    ExtensionAvatarResponseDTO getExtensionAvatars(String username);

    void setAllVisibilityFalse();

    AvatarCollectionResponseDTO getAvatarCollection();
}

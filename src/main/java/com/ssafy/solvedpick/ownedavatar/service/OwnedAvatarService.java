package com.ssafy.solvedpick.ownedavatar.service;

import com.ssafy.solvedpick.avatars.domain.Avatar;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.ownedavatar.domain.OwnedAvatar;
import com.ssafy.solvedpick.ownedavatar.dto.*;

import java.util.List;

public interface OwnedAvatarService {

    List<OwnedAvatarDTO> getOwnedAvatars(Long memberId);

    void updateAvatarVisibility(Long avatarId);

    AvatarSaleResponseDTO sellAvatars(AvatarSaleRequestDTO request);

    ExtensionAvatarResponseDTO getExtensionAvatars(String username);

    void setAllVisibilityFalse();

    AvatarCollectionResponseDTO getAvatarCollection();

    OwnedAvatar sellToAuction(Long id, Member member);

    void updateAvatarExtensionVisibility(Long ownedAvatarId);

    void cancelSold(OwnedAvatar ownedAvatar);

    void saveAvatar(Member member, Avatar avatar);

    void resetExtensionVisibility(Member member);
}

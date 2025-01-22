package com.ssafy.solvedpick.core.service;

import com.ssafy.solvedpick.common.enums.AvatarType;
import com.ssafy.solvedpick.common.enums.BackgroundType;
import com.ssafy.solvedpick.core.renderer.AvatarRenderer;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.ownedavatar.repository.OwnedAvatarRepository;
import com.ssafy.solvedpick.ownedbackgrounds.repository.OwnedBackgroundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompositionService {

    private final AvatarRenderer avatarRenderer;
    private final OwnedAvatarRepository ownedAvatarRepository;
    private final OwnedBackgroundRepository ownedBackgroundRepository;

    public String generateCompositeImage(Member member) {
        BackgroundType background = getBackgroundType(member);
        List<AvatarType> avatars = getAvatarTypes(member);

        return avatarRenderer.renderAvatars(background, avatars);
    }

    private BackgroundType getBackgroundType(Member member) {
        return ownedBackgroundRepository.findByMemberAndVisibleTrue(member)
                .map(bg -> BackgroundType.valueOf(bg.getBackground().getName()))
                .orElse(BackgroundType.SPACE_FIELD);
    }

    private List<AvatarType> getAvatarTypes(Member member) {
        return ownedAvatarRepository.findAllByMemberAndVisibleTrue(member)
                .stream()
                .map(avatar -> AvatarType.valueOf(avatar.getAvatar().getName()))
                .toList();
    }
}

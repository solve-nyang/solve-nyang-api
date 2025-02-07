package com.ssafy.solvedpick.composition.service;

import com.ssafy.solvedpick.common.enums.AvatarType;
import com.ssafy.solvedpick.common.enums.BackgroundType;
import com.ssafy.solvedpick.composition.renderer.CompositeRenderer;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.ownedavatar.repository.OwnedAvatarRepository;
import com.ssafy.solvedpick.ownedbackgrounds.repository.OwnedBackgroundRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompositionService {

    private final OwnedAvatarRepository ownedAvatarRepository;
    private final OwnedBackgroundRepository ownedBackgroundRepository;
    private final CompositeRenderer compositeRenderer;

    public String generateCompositeImage(Member member) {
        BackgroundType background = getBackgroundType(member);
        List<AvatarType> avatars = getAvatarTypes(member);
        return compositeRenderer.render(
                background,
                avatars,
                member.getUsername(),
                1,
                member.getSolvedCount(),
                member.getStreak());
    }

    private BackgroundType getBackgroundType(Member member) {
        return ownedBackgroundRepository.findByMemberAndVisibleTrue(member)
                .map(ownedBackground -> BackgroundType.fromName(ownedBackground.getBackground().getName()))
                .orElse(BackgroundType.fromName(BackgroundType.SPACE.getName()));
    }

    private List<AvatarType> getAvatarTypes(Member member) {
        return ownedAvatarRepository.findAllByMemberAndVisibleTrueAndSoldFalse(member)
                .stream()
                .map(avatar -> {
                    String avatarName = avatar.getAvatar().getName();
                    log.debug("Avatar Name: " + avatarName);

                    return AvatarType.fromName(avatarName);
                })
                .toList();
    }
}

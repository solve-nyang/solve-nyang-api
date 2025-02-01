package com.ssafy.solvedpick.composition.service;

import com.ssafy.solvedpick.common.enums.AvatarType;
import com.ssafy.solvedpick.common.enums.BackgroundType;
import com.ssafy.solvedpick.composition.renderer.AvatarRenderer;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.ownedavatar.repository.OwnedAvatarRepository;
import com.ssafy.solvedpick.ownedbackgrounds.repository.OwnedBackgroundRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
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
                .map(ownedBackground -> BackgroundType.fromName(ownedBackground.getBackground().getName()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST
                ));
    }

    private List<AvatarType> getAvatarTypes(Member member) {
        return ownedAvatarRepository.findAllByMemberAndVisibleTrueAndSoldFalse(member)
                .stream()
                .map(avatar -> {
                    String avatarName = avatar.getAvatar().getName();
                    log.debug("Avatar Name: " + avatarName);  // 출력

                    return AvatarType.fromName(avatarName);
                })
                .toList();
    }
}

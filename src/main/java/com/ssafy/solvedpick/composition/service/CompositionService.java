package com.ssafy.solvedpick.composition.service;

import com.ssafy.solvedpick.common.enums.AvatarType;
import com.ssafy.solvedpick.common.enums.BackgroundType;
import com.ssafy.solvedpick.composition.renderer.CompositeRenderer;
import com.ssafy.solvedpick.memberdisplay.domain.MemberDisplay;
import com.ssafy.solvedpick.memberdisplay.repository.MemberDisplayRepository;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.ownedavatar.repository.OwnedAvatarRepository;
import com.ssafy.solvedpick.ownedbackgrounds.repository.OwnedBackgroundRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompositionService {

    private final OwnedAvatarRepository ownedAvatarRepository;
    private final OwnedBackgroundRepository ownedBackgroundRepository;
    private final CompositeRenderer compositeRenderer;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${REDIS.CACHE_DURATION}")
    private long cacheDuration;

    public String generateCompositeImage(Member member) {
        String cachedImage = getCachedImage(member.getUsername());
        if (cachedImage != null) {
            return cachedImage;
        }

        String newImage = createNewImage(member);
        cacheImage(member.getUsername(), newImage);
        return newImage;
    }

    private String createNewImage(Member member) {
        BackgroundType background = getBackgroundType(member);
        List<AvatarType> avatars = getAvatarTypes(member);
        MemberDisplay memberDisplay = member.getMemberDisplay();

        return compositeRenderer.render(
                background,
                avatars,
                memberDisplay.getTitleVisible() ? memberDisplay.getTitle() : null,
                memberDisplay.getMemberClassVisible() ? memberDisplay.getMemberClass() : null,
                memberDisplay.getSolvedCountVisible() ? memberDisplay.getSolvedCount() : null,
                memberDisplay.getStreakVisible() ? memberDisplay.getStreak() : null
        );
    }

    private String getCachedImage(String username) {
        String cachedImage = redisTemplate.opsForValue().get(username);
        if(cachedImage == null) {
            log.debug("Cache missed: {}", username);
        } else {
            log.debug("Cache hit: {}", username);
        }
        return cachedImage;
    }

    private void cacheImage(String username, String image) {
        redisTemplate.opsForValue().set(username, image, cacheDuration, TimeUnit.SECONDS);
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

    public void invalidateImageCache(String username) {
        redisTemplate.delete(username);
    }
}

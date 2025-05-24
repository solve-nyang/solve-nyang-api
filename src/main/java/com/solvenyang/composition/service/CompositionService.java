package com.solvenyang.composition.service;

import com.solvenyang.common.enums.AvatarType;
import com.solvenyang.common.enums.BackgroundType;
import com.solvenyang.common.utils.point.Tier;
import com.solvenyang.composition.renderer.CompositeRenderer;
import com.solvenyang.memberdisplay.domain.MemberDisplay;
import com.solvenyang.memberdisplay.repository.MemberDisplayRepository;
import com.solvenyang.members.domain.Member;
import com.solvenyang.ownedavatar.repository.OwnedAvatarRepository;
import com.solvenyang.ownedbackgrounds.repository.OwnedBackgroundRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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
    private final MemberDisplayRepository memberDisplayRepository;

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
        MemberDisplay memberDisplay = memberDisplayRepository.findByMember(member)
                .orElseThrow(() -> new HttpClientErrorException(
                        HttpStatus.NOT_FOUND,
                        "해당 사용자의 세부 정보를 찾을 수 없습니다."
                ));

        return compositeRenderer.render(
                background,
                avatars,
                getDisplayTitle(memberDisplay, member.getUsername()),
                memberDisplay.getTierVisible() ? Tier.getTierfromLevel(memberDisplay.getTier()) : null,
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

    private String getDisplayTitle(MemberDisplay memberDisplay, String username) {
        if(!memberDisplay.getTitleVisible()){
            return null;
        }
        return memberDisplay.getTitle() != null ? memberDisplay.getTitle() : username;
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

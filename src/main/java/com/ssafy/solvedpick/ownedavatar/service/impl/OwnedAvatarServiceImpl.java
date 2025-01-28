package com.ssafy.solvedpick.ownedavatar.service.impl;

import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.avatars.service.AvatarService;
import com.ssafy.solvedpick.common.utils.grade.Grade;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.members.repository.MemberRepository;
import com.ssafy.solvedpick.ownedavatar.domain.OwnedAvatar;
import com.ssafy.solvedpick.ownedavatar.dto.*;
import com.ssafy.solvedpick.ownedavatar.repository.OwnedAvatarRepository;
import com.ssafy.solvedpick.ownedavatar.service.OwnedAvatarService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OwnedAvatarServiceImpl implements OwnedAvatarService {

    private static final int SALE_POINT_PER_AVATAR = 30;

    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final AvatarService avatarService;
    private final OwnedAvatarRepository ownedAvatarRepository;

    @Override
    public List<OwnedAvatarDTO> getOwnedAvatars(Long memberId) {
        return ownedAvatarRepository.findAllByMemberIdAndSoldFalse(memberId)
                .stream()
                .map(ownedAvatar -> OwnedAvatarDTO.builder()
                        .visible(ownedAvatar.getVisible())
                        .ownedAvatarId(ownedAvatar.getId())
                        .name(ownedAvatar.getAvatar().getName())
                        .rarity(Grade.fromValue(ownedAvatar.getAvatar().getGrade()).name())
                        .visibleExtension(ownedAvatar.getVisibleExtension())
                        .build())
                .toList();
    }

    @Override
    public void updateAvatarVisibility(Long avatarId) {
        Member member = authService.getCurrentMember();

        OwnedAvatar ownedAvatar = ownedAvatarRepository.findById(avatarId)
                .orElseThrow(() -> new EntityNotFoundException("No avatar"));

        if (!ownedAvatar.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("Not authorized to update this avatar");
        }
        ownedAvatar.updateVisibility();
    }

    @Override
    public ExtensionAvatarResponseDTO getExtensionAvatars(String username) {
        try {
            Member member = memberRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(""));

            List<String> avatars = ownedAvatarRepository.findAllByMemberAndVisibleExtensionTrueAndSoldFalse(member)
                    .stream()
                    .map(ownedAvatar -> ownedAvatar.getAvatar()
                            .getName())
                    .toList();

            return ExtensionAvatarResponseDTO.builder()
                    .avatars(avatars)
                    .build();
        } catch (Exception e) {
            return ExtensionAvatarResponseDTO.builder()
                    .avatars(new ArrayList<>())
                    .build();
        }
    }

    @Override
    public void setAllVisibilityFalse() {
        Member member = authService.getCurrentMember();
        ownedAvatarRepository.findAllByMemberAndVisibleTrueAndSoldFalse(member)
                .forEach(OwnedAvatar::updateVisibility);
    }

    @Override
    public AvatarCollectionResponseDTO getAvatarCollection() {
        Member member = authService.getCurrentMember();

        List<AvatarCollectionDTO> avatars = avatarService.getAllAvatars(member);

        return AvatarCollectionResponseDTO.builder()
                .collections(avatars)
                .build();
    }

    @Override
    public AvatarSaleResponseDTO sellAvatars(AvatarSaleRequestDTO request) {
        Member currentMember = authService.getCurrentMember();
        List<Long> idList = request.getSoldAvatars()
                .stream()
                .map(SoldAvatarDTO::getOwnedAvatarId)
                .toList();

        List<OwnedAvatar> result = ownedAvatarRepository.findAllByIdInAndMemberAndSoldFalse(idList, currentMember);
        result.forEach(OwnedAvatar::updateSold);

        int successCount = result.size();
        int totalPoints = successCount * SALE_POINT_PER_AVATAR;

        if (totalPoints > 0) {
            currentMember.addPoint(totalPoints);
        }

        return AvatarSaleResponseDTO.of(successCount, totalPoints);
    }
}

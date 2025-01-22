package com.ssafy.solvedpick.ownedavatar.service.impl;

import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.common.grade.Grade;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.members.repository.MemberRepository;
import com.ssafy.solvedpick.ownedavatar.domain.OwnedAvatar;
import com.ssafy.solvedpick.ownedavatar.dto.AvatarSaleRequestDto;
import com.ssafy.solvedpick.ownedavatar.dto.AvatarSaleResponseDto;
import com.ssafy.solvedpick.ownedavatar.dto.OwnedAvatarDTO;
import com.ssafy.solvedpick.ownedavatar.dto.SoldAvatarDto;
import com.ssafy.solvedpick.ownedavatar.repository.OwnedAvatarRepository;
import com.ssafy.solvedpick.ownedavatar.service.OwnedAvatarService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OwnedAvatarServiceImpl implements OwnedAvatarService {

    private final OwnedAvatarRepository ownedAvatarRepository;
    private final AuthService authService;
    private static final int SALE_POINT_PER_AVATAR = 30;
    private final MemberRepository memberRepository;

    @Override
    public List<OwnedAvatarDTO> getOwnedAvatars(Long memberId) {
        return ownedAvatarRepository.findAllByMemberIdAndSoldFalse(memberId)
                .stream()
                .map(ownedAvatar -> OwnedAvatarDTO.builder()
                        .visible(ownedAvatar.isVisible())
                        .ownedAvatarId(ownedAvatar.getId())
                        .name(ownedAvatar.getAvatar().getName())
                        .rarity(Grade.fromValue(ownedAvatar.getAvatar().getGrade()).name())
                        .dropRate(Grade.fromValue(ownedAvatar.getAvatar().getGrade()).getProbability())
                        .build())
                .toList();
    }

    //    Todo: member정보 확인
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
    public AvatarSaleResponseDto sellAvatars(AvatarSaleRequestDto request) {
        Member currentMember = authService.getCurrentMember();

        int successCount = 0;
        int totalPoints = 0;

        for (SoldAvatarDto soldAvatar : request.getSoldAvatars()) {
            try {
                boolean salesSuccess = processSingleAvatarSale(
                        soldAvatar.getOwnedAvatarId(),
                        currentMember
                );

                if (salesSuccess) {
                    successCount++;
                    totalPoints += SALE_POINT_PER_AVATAR;
                }
            } catch (Exception e) {
                continue;
            }
        }

        if (totalPoints > 0) {
            currentMember.addPoint(totalPoints);
            memberRepository.save(currentMember);
        }

        return AvatarSaleResponseDto.of(successCount, totalPoints);


    }

    private boolean processSingleAvatarSale(Long ownedAvatarId, Member currentMember) {
        OwnedAvatar ownedAvatar = ownedAvatarRepository.findById(ownedAvatarId)
                .orElseThrow(() -> new EntityNotFoundException("아바타를 찾을 수 없습니다: " + ownedAvatarId));

        if (!isValidForSale(ownedAvatar, currentMember)) {
            return false;
        }

        ownedAvatar.updateSold();
        ownedAvatarRepository.save(ownedAvatar);
        return true;
    }

    private boolean isValidForSale(OwnedAvatar ownedAvatar, Member currentMember) {
        if (ownedAvatar.isSold()) {
            return false;
        }

        if (!ownedAvatar.getMember().getId().equals(currentMember.getId())) {
            return false;
        }

        return true;
    }
}

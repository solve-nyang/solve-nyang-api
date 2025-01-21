package com.ssafy.solvedpick.ownedavatar.service.impl;

import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.common.grade.Grade;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.ownedavatar.domain.OwnedAvatar;
import com.ssafy.solvedpick.ownedavatar.dto.OwnedAvatarDTO;
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

    @Override
    public List<OwnedAvatarDTO> getOwnedAvatars(Long memberId) {
        return ownedAvatarRepository.findAllByMemberId(memberId)
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
}

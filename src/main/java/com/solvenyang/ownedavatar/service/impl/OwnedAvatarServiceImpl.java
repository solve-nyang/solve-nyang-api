package com.solvenyang.ownedavatar.service.impl;

import com.solvenyang.auth.service.AuthService;
import com.solvenyang.avatars.domain.Avatar;
import com.solvenyang.avatars.service.AvatarService;
import com.solvenyang.common.utils.grade.Grade;
import com.solvenyang.members.domain.Member;
import com.solvenyang.members.repository.MemberRepository;
import com.solvenyang.ownedavatar.domain.OwnedAvatar;
import com.solvenyang.ownedavatar.dto.*;
import com.solvenyang.ownedavatar.repository.OwnedAvatarRepository;
import com.solvenyang.ownedavatar.service.OwnedAvatarService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public ExtensionAvatarResponseDTO getExtensionAvatars(String username) {
        try {
            Member member = memberRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(""));

            List<String> avatars = ownedAvatarRepository.findAllByMemberAndVisibleExtensionTrueAndSoldFalse(
                    member.getId()
                    )
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
    @Transactional(readOnly = true)
    public AvatarCollectionResponseDTO getAvatarCollection() {
        Member member = authService.getCurrentMember();

        List<AvatarCollectionDTO> avatars = avatarService.getAllAvatars(member);

        return AvatarCollectionResponseDTO.builder()
                .collections(avatars)
                .build();
    }

    @Override
    public OwnedAvatar sellToAuction(Long id, Member member) {
        try {
            OwnedAvatar ownedAvatar = ownedAvatarRepository.findByIdAndMemberAndSoldFalse(id, member)
                    .orElseThrow(IllegalAccessException::new);
            ownedAvatar.setSold();

            return ownedAvatar;
        } catch (IllegalAccessException e) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "판매중인 아바타입니다.");
        }
    }

    @Override
    public void updateAvatarExtensionVisibility(Long ownedAvatarId) {
        Member currentMember = authService.getCurrentMember();

        try {
            OwnedAvatar ownedAvatar = ownedAvatarRepository.findByIdAndMemberAndSoldFalse(ownedAvatarId, currentMember)
                    .orElseThrow(IllegalAccessException::new);

            ownedAvatar.updateExtensionVisibility();
        } catch (IllegalAccessException e) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void cancelSold(OwnedAvatar ownedAvatar) {
        ownedAvatar.clearSold();
    }

    @Override
    public void saveAvatar(Member member, Avatar avatar) {
        OwnedAvatar ownedAvatar = OwnedAvatar.builder()
                .member(member)
                .avatar(avatar)
                .build();

        ownedAvatarRepository.save(ownedAvatar);
    }

    @Override
    public void resetExtensionVisibility(Member member) {
        ownedAvatarRepository.setVisibleExtensionFalse(member.getId());
    }

    @Override
    public AvatarSaleResponseDTO sellAvatars(AvatarSaleRequestDTO request) {
        Member currentMember = authService.getCurrentMember();
        List<Long> idList = request.getSoldAvatars()
                .stream()
                .map(SoldAvatarDTO::getOwnedAvatarId)
                .toList();

        List<OwnedAvatar> result = ownedAvatarRepository.findAllByIdInAndMemberAndSoldFalse(idList, currentMember);
        result.forEach(OwnedAvatar::setSold);

        int successCount = result.size();
        int totalPoints = successCount * SALE_POINT_PER_AVATAR;

        if (totalPoints > 0) {
            currentMember.addPoint(totalPoints);
        }

        return AvatarSaleResponseDTO.of(successCount, totalPoints);
    }
}

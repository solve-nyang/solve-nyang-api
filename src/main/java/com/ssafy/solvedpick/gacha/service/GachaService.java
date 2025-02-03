package com.ssafy.solvedpick.gacha.service;

import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.avatars.domain.Avatar;
import com.ssafy.solvedpick.common.utils.grade.Grade;
import com.ssafy.solvedpick.ownedavatar.domain.OwnedAvatar;
import com.ssafy.solvedpick.avatars.dto.DrawAvatarDto;
import com.ssafy.solvedpick.avatars.repository.AvatarRepository;
import com.ssafy.solvedpick.ownedavatar.repository.OwnedAvatarRepository;
import com.ssafy.solvedpick.gacha.dto.DrawResponse;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.members.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GachaService {

    private static final int DRAW_COST = 100;

    private final MemberRepository memberRepository;
    private final AvatarRepository avatarRepository;
    private final OwnedAvatarRepository ownedAvatarRepository;
    private final Random random = new Random();
    private final AuthService authService;

    @Transactional
    public DrawResponse drawAvatars(int count){

        Member member = authService.getCurrentMember();

        if (count != 1 && count != 10) {
            throw new IllegalArgumentException("가챠는 1회 또는 10회만 가능합니다.");
        }

        int totalCost = count * DRAW_COST;
        if (member.getPoint() < totalCost) {
            throw new IllegalArgumentException("포인트가 부족합니다. 필요 포인트: " + totalCost + ", 보유 포인트: " + member.getPoint());
        }

        member.usePoint(totalCost);
        this.memberRepository.save(member);
        List<DrawAvatarDto> results = new ArrayList<>();

        for(int i=0; i<count; i++){
            Grade selectedGrade = selectGradeByRandom();

            List<Avatar> avatarsOfGrade = avatarRepository.findAllByGrade(selectedGrade.getValue());
            if (avatarsOfGrade.isEmpty()){
                continue;
            }
            Avatar selectedAvatar = avatarsOfGrade.get(random.nextInt(avatarsOfGrade.size()));

            OwnedAvatar ownedAvatar = OwnedAvatar.builder()
                    .member(member)
                    .avatar(selectedAvatar)
                    .visible(false)
                    .build();

            ownedAvatarRepository.save(ownedAvatar);

            results.add(DrawAvatarDto.builder()
                    .ownedAvatarId(ownedAvatar.getId())
                    .avatarId(selectedAvatar.getId())
                    .name(selectedAvatar.getName())
                    .rarity(Grade.fromValue(selectedAvatar.getGrade()).name())
                    .dropRate(Grade.fromValue(selectedAvatar.getGrade()).getProbability())
                    .build());
        }

        return DrawResponse.builder()
                .avatars(results)
                .build();
    }

    @Transactional
    public boolean getEventAvatar(String avatarName) {
        Member member = authService.getCurrentMember();
        Avatar eventAvatar = avatarRepository.findByName(avatarName);

        if (ownedAvatarRepository.existsByMemberAndAvatar(member, eventAvatar)) {
            return false;
        }

        OwnedAvatar ownedAvatar = OwnedAvatar.builder()
                .member(member)
                .avatar(eventAvatar)
                .build();

        ownedAvatarRepository.save(ownedAvatar);
        return true;
    }

    @Transactional
    public boolean hasEventAvatar(String avatarName) {
        Member member = authService.getCurrentMember();
        Avatar eventAvatar = avatarRepository.findByName(avatarName);

        return ownedAvatarRepository.existsByMemberAndAvatar(member, eventAvatar);
    }

    private Grade selectGradeByRandom() {
        double randomValue = random.nextDouble() * 100;
        double accumulatedProbability = 0.0;

        for (Grade grade : Grade.values()) {
            accumulatedProbability += grade.getProbability();
            if (randomValue <= accumulatedProbability) {
                return grade;
            }
        }

        return Grade.D;
    }
}

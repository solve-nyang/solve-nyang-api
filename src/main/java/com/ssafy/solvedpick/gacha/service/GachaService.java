package com.ssafy.solvedpick.gacha.service;

import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.avatars.domain.Avatar;
import com.ssafy.solvedpick.common.grade.Grade;
import com.ssafy.solvedpick.ownedavatar.domain.OwnedAvatar;
import com.ssafy.solvedpick.avatars.dto.DrawAvatarDto;
import com.ssafy.solvedpick.avatars.repository.AvatarRepository;
import com.ssafy.solvedpick.ownedavatar.repository.OwnedAvatarRepository;
import com.ssafy.solvedpick.gacha.dto.DrawResponse;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.members.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GachaService {

//    임시 코스트
    private static final int DRAW_COST = 1000;

    private final MemberRepository memberRepository;
    private final AvatarRepository avatarRepository;
    private final OwnedAvatarRepository ownedAvatarRepository;
    private final Random random = new Random();
    private final AuthService authService;

    public DrawResponse drawAvatars(int count){
//        더미 유저
        Member member = authService.getCurrentMember();

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

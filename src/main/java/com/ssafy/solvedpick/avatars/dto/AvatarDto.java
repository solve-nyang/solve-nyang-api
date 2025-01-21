package com.ssafy.solvedpick.avatars.dto;


import com.ssafy.solvedpick.avatars.domain.Avatar;

import com.ssafy.solvedpick.common.grade.Grade;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AvatarDto {

    private String id; //API 응답용 ID
    private String name; //캐릭터 이름
    private String rarity; //등급 (알파벳)
    private double dropRate; //뽑힐 확률

    //Entity를 DTO로 변환하는 메서드
    public static AvatarDto from(Avatar avatar, GradeStatistics statistics) {
        Grade grade = Grade.fromValue(avatar.getGrade());

        //Entity와 Enum의 정보들을 바탕으로 DTO로 변환
        return AvatarDto.builder()
                .id(String.valueOf(avatar.getId()))
                .name(avatar.getName())
                .rarity(grade.name())
                .dropRate(statistics.getIndividualProbability())
                .build();
    }
}

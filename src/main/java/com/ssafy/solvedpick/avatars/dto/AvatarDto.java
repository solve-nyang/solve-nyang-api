package com.ssafy.solvedpick.avatars.dto;


import com.ssafy.solvedpick.avatars.domain.Avatar;
import com.ssafy.solvedpick.avatars.domain.Grade;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class AvatarDto {

    private String id; //API 응답용 ID
    private String name; //캐릭터 이름
    private String rarity; //등급 (알파벳)
    private double dropRate; //뽑힐 확률

    //Entity를 DTO로 변환하는 메서드
    public static AvatarDto toDto(Avatar avatar, Map<Integer, Long> countByGrade) {
        Grade grade = Grade.fromValue(avatar.getGrade());
        // 해당 등급의 전체 캐릭터 수를 가져옴
        long charactersInGrade = countByGrade.get(avatar.getGrade());

        //개별 캐릭터의 확률을 계산
        double individualDropRate = grade.getProbability() / charactersInGrade;

        //Entity와 Enum의 정보들을 바탕으로 DTO로 변환
        return AvatarDto.builder()
                .id(String.valueOf(avatar.getId()))
                .name(avatar.getTitle())
                .rarity(grade.name())
                .dropRate(individualDropRate)
                .build();
    }
}

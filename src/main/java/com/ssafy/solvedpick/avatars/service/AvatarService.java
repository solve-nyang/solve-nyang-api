package com.ssafy.solvedpick.avatars.service;

import com.ssafy.solvedpick.avatars.domain.Avatar;
import com.ssafy.solvedpick.avatars.dto.AvatarDto;
import com.ssafy.solvedpick.avatars.dto.AvatarResponse;
import com.ssafy.solvedpick.avatars.repository.AvatarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvatarService {
    private final AvatarRepository avatarRepository;

    public AvatarResponse findAllAvatars() {
        // 1. 모든 아바타를 가져옵니다.
        List<Avatar> avatars = avatarRepository.findAll();
        //2. 등급별 캐릭터 수를 계산합니다.
        Map<Integer, Long> countByGrade = avatars.stream()
                .collect(Collectors.groupingBy(
                        Avatar::getGrade, //등급으로 그룹화
                        Collectors.counting() // 각 등급별 갯수를 셈
                ));
        // 3. Entity들을 DTO로 변환하면서 Map을 전달합니다.
        List<AvatarDto> avatarDtos = avatars.stream()
                .map(avatar -> AvatarDto.toDto(avatar, countByGrade))
                .collect(Collectors.toList());

        //4. 최종 응답 생성
        return AvatarResponse.of(avatarDtos);
    }
}

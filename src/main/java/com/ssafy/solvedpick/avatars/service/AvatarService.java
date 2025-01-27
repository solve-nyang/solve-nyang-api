package com.ssafy.solvedpick.avatars.service;

import com.ssafy.solvedpick.avatars.domain.Avatar;

import com.ssafy.solvedpick.avatars.dto.AvatarDto;
import com.ssafy.solvedpick.avatars.dto.AvatarResponse;
import com.ssafy.solvedpick.avatars.dto.GradeStatistics;
import com.ssafy.solvedpick.avatars.repository.AvatarRepository;
import com.ssafy.solvedpick.common.utils.grade.Grade;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.ownedavatar.dto.AvatarCollectionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvatarService {
    private final AvatarRepository avatarRepository;

    public AvatarResponse findAllAvatars() {

        List<Avatar> avatars = avatarRepository.findAllByGradeBetween(1, 5);

        List<GradeStatistics> gradeStats = calculateGradeStatistics(avatars);

        List<AvatarDto> avatarDtos = avatars.stream()
                .map(avatar -> AvatarDto.from(avatar,
                        findStatisticsByGrade(gradeStats, avatar.getGrade())
                )).collect(Collectors.toList());

        return AvatarResponse.of(avatarDtos);
    }

    public List<AvatarCollectionDTO> getAllAvatars(Member member) {
        return avatarRepository.getAvatarCollections(member.getId());
    }

    private List<GradeStatistics> calculateGradeStatistics(List<Avatar> avatars) {
        List<GradeStatistics> statistics = new ArrayList<>();


        for (int grade=1;grade<=5;grade++) {
            final int currentGrade = grade;
            long count = avatars.stream().
                    filter(avatar -> avatar.getGrade() == currentGrade)
                    .count();

            if (count > 0) {
                double probability = Grade.fromValue(grade).getProbability();
                double individualProbability = probability/count;
                statistics.add(new GradeStatistics(
                        grade,
                        count,
                        probability,
                        individualProbability
                ));
            }
        }
        return statistics;
    }

    private GradeStatistics findStatisticsByGrade(List<GradeStatistics> statistics, int grade) {
        return statistics.stream()
                .filter(stat -> stat.getGrade()==grade)
                .findFirst()
                .orElseThrow(()-> new IllegalStateException("해당 등급의 통계 정보를 찾을 수 없습니다"));

    }
}

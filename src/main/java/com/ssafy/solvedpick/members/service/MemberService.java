package com.ssafy.solvedpick.members.service;

import com.ssafy.solvedpick.common.utils.point.Point;
import com.ssafy.solvedpick.members.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ssafy.solvedpick.api.dto.SolvedProblemsApiResponse;
import com.ssafy.solvedpick.api.dto.UserData;
import com.ssafy.solvedpick.api.dto.UserInfoApiResponse;
import com.ssafy.solvedpick.api.service.ApiService;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.members.dto.UserInfoResponse;
import com.ssafy.solvedpick.problem.domain.Problem;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final ApiService apiService;
    private final MemberRepository memberRepository;

    // TODO: 전체 수정하기
    public UserInfoResponse getUserInfo(Member member) {

        return UserInfoResponse.builder()
                .username(member.getUsername())
                .point(member.getPoint())
                .tier(Point.getPointName(member.getTier()))
                .solvedCount(member.getSolvedCount())
                .streak(member.getStreak())
                .build();
    }

    @Transactional
    public void updateUserProcess(Member member) {
        UserInfoApiResponse apiResponse = apiService.getUserInfo(member.getUsername());
        UserData userData = apiResponse.getItems().get(0);

        log.debug("userData : {}", userData);
        SolvedProblemsApiResponse newProblems = apiService.getSolvedProblems(member.getUsername());
        Problem problem = member.getSolvedProblems();

        problem.updateSolvedProblems(newProblems);
        member.updateInfo(userData.getTier(), userData.getSolvedCount(), userData.getMaxStreak());

        log.debug("member: {}", member);
        memberRepository.save(member);
    }
}
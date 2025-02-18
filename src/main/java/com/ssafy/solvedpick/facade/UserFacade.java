package com.ssafy.solvedpick.facade;

import com.ssafy.solvedpick.api.dto.SolvedProblemsApiResponse;
import com.ssafy.solvedpick.api.dto.UserData;
import com.ssafy.solvedpick.api.service.ApiService;
import com.ssafy.solvedpick.common.utils.point.Tier;
import com.ssafy.solvedpick.memberdisplay.domain.MemberDisplay;
import com.ssafy.solvedpick.memberdisplay.service.MemberDisplayService;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.members.dto.UserProfileResponse;
import com.ssafy.solvedpick.problem.domain.Problem;
import com.ssafy.solvedpick.problem.service.ProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFacade {
    private final ProblemService problemService;
    private final ApiService apiService;
    private final MemberDisplayService memberDisplayService;

    @Transactional
    public void initializeNewUserInfo(Member member) {
        Problem problem = Problem.initSolvedProblems(member);
        problemService.save(problem);

        MemberDisplay memberDisplay = MemberDisplay.initMemberDisplay(member);
        memberDisplayService.save(memberDisplay);

        syncUserInfo(member);
    }

    @Transactional
    public void syncUserInfo(Member member) {
        log.info("syncUserInfo start");
        UserData userData = apiService.getUserInfo(member.getUsername());

        log.info("syncUserInfo getUserData:{}", userData);
        SolvedProblemsApiResponse newProblems = apiService.getSolvedProblems(member.getUsername());
        Problem problem = problemService.findByMember(member);

        problemService.updateSolvedProblems(problem, newProblems);

        MemberDisplay memberDisplay = memberDisplayService.findByMember(member);
        memberDisplayService.updateMemberDisplay(memberDisplay, userData);
    }

    public UserProfileResponse getUserProfile(Member member) {
        MemberDisplay memberDisplay = memberDisplayService.findByMember(member);

        return UserProfileResponse.builder()
                .username(member.getUsername())
                .point(member.getPoint())
                .memberClass(memberDisplay.getMemberClass())
                .tier(Tier.getTierName(memberDisplay.getTier()))
                .solvedCount(memberDisplay.getSolvedCount())
                .streak(memberDisplay.getStreak())
                .build();
    }
}


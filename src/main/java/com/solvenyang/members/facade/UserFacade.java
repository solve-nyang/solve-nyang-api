package com.solvenyang.members.facade;

import com.solvenyang.api.dto.SolvedProblemsApiResponse;
import com.solvenyang.api.dto.UserData;
import com.solvenyang.api.service.ApiService;
import com.solvenyang.common.utils.point.Tier;
import com.solvenyang.memberdisplay.domain.MemberDisplay;
import com.solvenyang.memberdisplay.service.MemberDisplayService;
import com.solvenyang.members.domain.Member;
import com.solvenyang.members.dto.UserProfileResponse;
import com.solvenyang.problem.domain.Problem;
import com.solvenyang.problem.service.ProblemService;
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


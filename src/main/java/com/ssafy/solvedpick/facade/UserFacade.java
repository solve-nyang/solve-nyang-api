package com.ssafy.solvedpick.facade;

import com.ssafy.solvedpick.api.dto.SolvedProblemsApiResponse;
import com.ssafy.solvedpick.api.dto.UserData;
import com.ssafy.solvedpick.api.service.ApiService;
import com.ssafy.solvedpick.memberdisplay.domain.MemberDisplay;
import com.ssafy.solvedpick.memberdisplay.service.MemberDisplayService;
import com.ssafy.solvedpick.members.domain.Member;
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
        member.initMemberDisplay(memberDisplay);

        syncUserInfo(member);
    }

    @Transactional
    public void syncUserInfo(Member member) {
        log.info("syncUserInfo start");
        UserData userData = apiService.getUserInfo(member.getUsername());

        log.info("syncUserInfo getUserData:{}", userData);
        SolvedProblemsApiResponse newProblems = apiService.getSolvedProblems(member.getUsername());
        Problem problem = problemService.findByMember(member);

        log.info("syncUserInfo Problem:{}, newProblem:{}", problem, newProblems.size());
        problemService.updateSolvedProblems(problem, newProblems);

        log.info("syncUserInfo updaeSolvedProblems");
        MemberDisplay memberDisplay = member.getMemberDisplay();
        memberDisplayService.updateMemberDisplay(memberDisplay, userData);
        log.info("syncUserInfo end(update memberDisplay)");
    }

    public int getCurrentSolvedCount(String username) {
        UserData response = apiService.getUserInfo(username);
        if (response == null) {
            throw new RuntimeException("User not found: " + username);
        }

        return response.getSolvedCount();
    }

}


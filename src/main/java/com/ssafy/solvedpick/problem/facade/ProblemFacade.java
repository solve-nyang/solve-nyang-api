package com.ssafy.solvedpick.problem.facade;

import com.ssafy.solvedpick.api.dto.SolvedProblemsApiResponse;
import com.ssafy.solvedpick.api.dto.UserData;
import com.ssafy.solvedpick.api.dto.UserInfoApiResponse;
import com.ssafy.solvedpick.api.service.ApiService;
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
public class ProblemFacade {
    private final ProblemService problemService;
    private final ApiService apiService;

    @Transactional
    public void initializeNewUserProblem(Member member) {

        Problem problem = Problem.initSolvedProblems(member);
        member.initSolvedProblem(problem);
        syncUserProblemInfo(member);
    }

    @Transactional
    public void syncUserProblemInfo(Member member) {

        UserInfoApiResponse apiResponse = apiService.getUserInfo(member.getUsername());
        UserData userData = apiResponse.getItems().get(0);

        SolvedProblemsApiResponse newProblems = apiService.getSolvedProblems(member.getUsername());
        Problem problem = member.getSolvedProblems();

        problemService.updateSolvedProblems(problem, newProblems);
        member.updateInfo(userData.getTier(), userData.getSolvedCount(), userData.getMaxStreak());
    }
}


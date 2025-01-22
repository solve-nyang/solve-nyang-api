package com.ssafy.solvedpick.members.service;

import org.springframework.stereotype.Service;

import com.ssafy.solvedpick.api.dto.SolvedProblemsApiResponse;
import com.ssafy.solvedpick.api.dto.UserData;
import com.ssafy.solvedpick.api.dto.UserInfoApiResponse;
import com.ssafy.solvedpick.api.service.ApiService;
import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.members.dto.UserInfoResponse;
import com.ssafy.solvedpick.members.repository.MemberRepository;
import com.ssafy.solvedpick.problem.domain.Problem;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ApiService apiService;
    private final AuthService authService;

    // TODO: 전체 수정하기
    public UserInfoResponse getUserInfo() {
        Member member = authService.getCurrentMember();

        UserInfoApiResponse apiResponse = apiService.getUserInfo(member.getUsername());
        UserData userData = apiResponse.getItems().get(0);
        
    	SolvedProblemsApiResponse newProblems = apiService.getSolvedProblems(member.getUsername());
    	Problem problem = member.getSolvedProblems();

    	problem.updateSolvedProblems(newProblems);
    	this.memberRepository.save(member);
        return UserInfoResponse.builder()
                .username(member.getUsername())
                .point(member.getPoint())
                .solvedacTier(userData.getTier())
                .solvedCount(userData.getSolvedCount())
                .solvedacStrick(userData.getMaxStreak())
                .build();
    }
}
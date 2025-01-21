package com.ssafy.solvedpick.members.service;

import com.ssafy.solvedpick.api.dto.UserData;
import com.ssafy.solvedpick.api.dto.UserInfoApiResponse;
import com.ssafy.solvedpick.api.service.ApiService;
import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.members.dto.UserInfoResponse;
import com.ssafy.solvedpick.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ApiService apiService;
    private final AuthService authService;

    // TODO: 전체 수정하기
    // TODO: point 로직 추가
    public UserInfoResponse getUserInfo() {
        Member member = authService.getCurrentMember();

        UserInfoApiResponse apiResponse = apiService.getUserInfo(member.getUsername());
        UserData userData = apiResponse.getItems().get(0);

        return UserInfoResponse.builder()
                .nickname(member.getUsername())
                .point(member.getPoint())
                .solvedacTier(userData.getTier())
                .solvedCount(userData.getSolvedCount())
                .solvedacStrick(userData.getMaxStreak())
                .build();
    }
}
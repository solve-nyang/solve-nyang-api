package com.ssafy.solvedpick.members.service;

import com.ssafy.solvedpick.api.dto.UserData;
import com.ssafy.solvedpick.api.dto.UserInfoApiResponse;
import com.ssafy.solvedpick.api.service.ApiService;
import com.ssafy.solvedpick.members.dto.UserInfoResponse;
import com.ssafy.solvedpick.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ApiService apiService;
    
    // TODO: 전체 수정하기
    public UserInfoResponse getUserInfo(Long memberId) {
        String memberName = memberRepository.findById(memberId)
                .orElseThrow(RuntimeException::new)
                .getUsername();

        UserInfoApiResponse apiResponse = apiService.getUserInfo(memberName);
        UserData userData = apiResponse.getItems().get(0);

        return UserInfoResponse.builder()
                .nickname(memberName)
//                더미 데이터
                .point(123456)
                .solvedacTier(userData.getTier())
                .solvedCount(userData.getSolvedCount())
                .solvedacStrick(userData.getMaxStreak())
                .build();
    }
}
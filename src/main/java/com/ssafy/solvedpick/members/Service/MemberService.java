package com.ssafy.solvedpick.members.Service;

import com.ssafy.solvedpick.api.dto.UserData;
import com.ssafy.solvedpick.api.dto.UserInfoApiResponse;
import com.ssafy.solvedpick.api.service.ApiService;
import com.ssafy.solvedpick.members.dto.UserInfoResponse;
import com.ssafy.solvedpick.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ApiService apiService;
    //        더미아이디이름
    @Value("${API.ID}")
    private String membername;

    public UserInfoResponse getUserInfo(Long memberId) {
//        String membername = memberRepository.findById(memberId)
//                .orElseThrow(EntityNotFoundException::new)
//                .getUsername();

        UserInfoApiResponse apiResponse = apiService.getUserInfo(membername);
        UserData userData = apiResponse.getItems().get(0);

        return UserInfoResponse.builder()
                .nickname(membername)
//                더미 데이터
                .point(123456)
                .solvedacTier(userData.getTier())
                .solvedCount(userData.getSolvedCount())
                .solvedacStrick(userData.getMaxStreak())
                .build();
    }
}

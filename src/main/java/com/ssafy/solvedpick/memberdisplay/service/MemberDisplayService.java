package com.ssafy.solvedpick.memberdisplay.service;

import com.ssafy.solvedpick.api.dto.UserData;
import com.ssafy.solvedpick.memberdisplay.domain.MemberDisplay;
import com.ssafy.solvedpick.memberdisplay.dto.DisplayVisibilityResponse;
import com.ssafy.solvedpick.memberdisplay.repository.MemberDisplayRepository;
import com.ssafy.solvedpick.members.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberDisplayService {

    private final MemberDisplayRepository memberDisplayRepository;

    @Transactional
    public void updateMemberDisplay(MemberDisplay memberDisplay, UserData userData) {
        memberDisplay.updateInfo(userData);
    }

    @Transactional(readOnly = true)
    public DisplayVisibilityResponse getDisplaySetting(Member member) {
        MemberDisplay memberDisplay = member.getMemberDisplay();

        return DisplayVisibilityResponse.builder()
                .memberClassVisible(memberDisplay.getMemberClassVisible())
                .tierVisible(memberDisplay.getTierVisible())
                .titleVisible(memberDisplay.getTitleVisible())
                .streakVisible(memberDisplay.getStreakVisible())
                .solvedCountVisible(memberDisplay.getSolvedCountVisible())
                .build();
    }


}

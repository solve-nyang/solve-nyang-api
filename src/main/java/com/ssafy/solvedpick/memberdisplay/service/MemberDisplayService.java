package com.ssafy.solvedpick.memberdisplay.service;

import com.ssafy.solvedpick.api.dto.UserData;
import com.ssafy.solvedpick.memberdisplay.domain.MemberDisplay;
import com.ssafy.solvedpick.memberdisplay.dto.DisplayVisibilityResponse;
import com.ssafy.solvedpick.memberdisplay.repository.MemberDisplayRepository;
import com.ssafy.solvedpick.members.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

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

    @Transactional
    public void toggleTier(Member member) {
        MemberDisplay memberDisplay = member.getMemberDisplay();
        validateMemberDisplay(member, memberDisplay);
        memberDisplay.toggleTierVisibility();
    }

    @Transactional
    public void toggleMemberClass(Member member) {
        MemberDisplay memberDisplay = member.getMemberDisplay();
        validateMemberDisplay(member, memberDisplay);
        memberDisplay.toggleMemberClassVisibility();
    }

    @Transactional
    public void toggleTitle(Member member) {
        MemberDisplay memberDisplay = member.getMemberDisplay();
        validateMemberDisplay(member, memberDisplay);
        memberDisplay.toggleTitleVisibility();
    }

    @Transactional
    public void toggleSolvedCount(Member member) {
        MemberDisplay memberDisplay = member.getMemberDisplay();
        validateMemberDisplay(member, memberDisplay);
        memberDisplay.toggleSolvedCountVisibility();
    }

    @Transactional
    public void toggleStreak(Member member) {
        MemberDisplay memberDisplay = member.getMemberDisplay();
        validateMemberDisplay(member, memberDisplay);
        memberDisplay.toggleStreakVisibility();
    }


    private void validateMemberDisplay(Member member, MemberDisplay memberDisplay) {
        if (!memberDisplay.getMember().getId().equals(member.getId())) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "접근 권한이 없습니다.");
        }
    }
}

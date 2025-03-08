package com.ssafy.solvedpick.memberdisplay.service;

import com.ssafy.solvedpick.api.dto.UserData;
import com.ssafy.solvedpick.memberdisplay.domain.MemberDisplay;
import com.ssafy.solvedpick.memberdisplay.dto.DisplayTitleRequest;
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
        MemberDisplay memberDisplay = findByMember(member);

        return DisplayVisibilityResponse.builder()
                .title(memberDisplay.getTitle()!=null ? memberDisplay.getTitle() : member.getUsername())
                .memberClassVisible(memberDisplay.getMemberClassVisible())
                .tierVisible(memberDisplay.getTierVisible())
                .titleVisible(memberDisplay.getTitleVisible())
                .streakVisible(memberDisplay.getStreakVisible())
                .solvedCountVisible(memberDisplay.getSolvedCountVisible())
                .build();
    }

    @Transactional
    public void setDisplayTitle(Member member,DisplayTitleRequest displayTitleRequest) {
        MemberDisplay memberDisplay = findByMember(member);
        String title = displayTitleRequest.getTitle();
        validateTitle(title);
        memberDisplay.updateTitle(title);
    }

    private void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "제목은 필수 입력값입니다.");
        }
        if (title.length() > 20) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "제목은 20자를 초과할 수 없습니다.");
        }
        if (!title.matches("^[a-zA-Z0-9]+$")) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "제목은 영문자와 숫자만 사용 가능합니다.");
        }
    }

    @Transactional
    public void toggleTier(Member member) {
        MemberDisplay memberDisplay = findByMember(member);
        memberDisplay.toggleTierVisibility();
    }

    @Transactional
    public void toggleMemberClass(Member member) {
        MemberDisplay memberDisplay = findByMember(member);
        memberDisplay.toggleMemberClassVisibility();
    }

    @Transactional
    public void toggleTitle(Member member) {
        MemberDisplay memberDisplay = findByMember(member);
        memberDisplay.toggleTitleVisibility();
    }

    @Transactional
    public void toggleSolvedCount(Member member) {
        MemberDisplay memberDisplay = findByMember(member);
        memberDisplay.toggleSolvedCountVisibility();
    }

    @Transactional
    public void toggleStreak(Member member) {
        MemberDisplay memberDisplay = findByMember(member);
        memberDisplay.toggleStreakVisibility();
    }

    public MemberDisplay findByMember(Member member){
        return memberDisplayRepository.findByMember(member)
                .orElseThrow(() -> new HttpClientErrorException(
                        HttpStatus.NOT_FOUND,
                        "해당 사용자의 세부 정보를 찾을 수 없습니다."
                ));
    }

    public void save(MemberDisplay memberDisplay) {
        memberDisplayRepository.save(memberDisplay);
    }
}

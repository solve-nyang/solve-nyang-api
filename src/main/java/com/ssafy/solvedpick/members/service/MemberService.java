package com.ssafy.solvedpick.members.service;

import com.ssafy.solvedpick.common.utils.point.Point;
import com.ssafy.solvedpick.facade.UserFacade;
import com.ssafy.solvedpick.memberdisplay.domain.MemberDisplay;
import com.ssafy.solvedpick.members.dto.BasicUserInfoResponse;
import com.ssafy.solvedpick.members.dto.UserProfileResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.ssafy.solvedpick.members.domain.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private static final double FEE = 0.95;

    private final UserFacade userFacade;

    public BasicUserInfoResponse getUserInfo(Member member) {

        return BasicUserInfoResponse.builder()
                .username(member.getUsername())
                .point(member.getPoint())
                .build();
    }

    @Transactional
    public void updateUserProcess(Member member) {
        userFacade.syncUserInfo(member);
    }

    public UserProfileResponse getUserProfile(Member member) {
        MemberDisplay memberDisplay = member.getMemberDisplay();

        return UserProfileResponse.builder()
                .username(member.getUsername())
                .point(member.getPoint())
                .memberClass(memberDisplay.getMemberClass())
                .tier(Point.getPointName(memberDisplay.getTier()))
                .solvedCount(memberDisplay.getSolvedCount())
                .streak(memberDisplay.getStreak())
                .build();
    }

    public void sellAvatar(Member seller, Long point) {
        long result = (long) Math.ceil(point * FEE);
        seller.addPoint(result);
    }

    public void buyAvatar(Member buyer, Long point) {
        if (buyer.getPoint() < point) {
            throw new HttpClientErrorException(HttpStatus.PAYMENT_REQUIRED);
        }

        buyer.usePoint(point);
    }
}
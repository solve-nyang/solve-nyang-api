package com.ssafy.solvedpick.members.service;

import com.ssafy.solvedpick.facade.UserFacade;
import com.ssafy.solvedpick.members.dto.BasicUserInfoResponse;
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
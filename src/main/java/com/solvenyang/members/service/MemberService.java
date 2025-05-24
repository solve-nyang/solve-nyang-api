package com.solvenyang.members.service;

import com.solvenyang.members.facade.UserFacade;
import com.solvenyang.members.dto.BasicUsernameResponse;
import com.solvenyang.members.dto.UserPointResponse;
import com.solvenyang.members.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.solvenyang.members.domain.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private static final double FEE = 0.95;

    private final UserFacade userFacade;
    private final MemberRepository memberRepository;

    public BasicUsernameResponse getUsername(Member member) {

        return BasicUsernameResponse.builder()
                .username(member.getUsername())
                .build();
    }

    @Transactional
    public void updateUserProcess(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalStateException("Member not found: " + memberId));
        log.info("update user process: {}", member.getUsername());
        userFacade.syncUserInfo(member);
    }
    
    public UserPointResponse getUserPoint(Member member){
        return UserPointResponse.builder()
                .point(member.getPoint())
                .build();
    }

    public void sellAvatar(Member seller, Long point) {
        long result = (long) Math.ceil(point * FEE);
        seller.addPoint(result);
    }

    public void buyAvatar(Member buyer, Long point) {
        if (buyer.getPoint() < point) {
            throw new HttpClientErrorException(HttpStatus.PAYMENT_REQUIRED, "남은 포인트가 부족합니다.");
        }

        buyer.usePoint(point);
    }
}
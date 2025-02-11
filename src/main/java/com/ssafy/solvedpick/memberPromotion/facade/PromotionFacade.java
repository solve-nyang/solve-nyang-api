package com.ssafy.solvedpick.memberPromotion.facade;

import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.memberPromotion.dto.CoinResponseDTO;
import com.ssafy.solvedpick.memberPromotion.service.PromotionService;
import com.ssafy.solvedpick.members.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PromotionFacade {

    private final PromotionService promotionService;
    private final AuthService authService;

    public CoinResponseDTO getMemberCoin() {
        Member member = authService.getCurrentMember();
        int coin = promotionService.getMemberCoin(member);

        return CoinResponseDTO.builder()
                .coin(coin)
                .build();
    }
}

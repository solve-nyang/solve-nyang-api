package com.ssafy.solvedpick.memberPromotion.service;

import com.ssafy.solvedpick.memberPromotion.domain.Promotion;
import com.ssafy.solvedpick.memberPromotion.repository.PromotionRepository;
import com.ssafy.solvedpick.members.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository promotionRepository;

    @Transactional(readOnly = true)
    public int getMemberCoin(Member member) {
        Promotion promotion = promotionRepository.findByMember(member)
                .orElseGet(() -> save(member));

        return promotion.getCoin();
    }

    public Promotion save(Member member) {
        Promotion promotion = Promotion.builder()
                .member(member)
                .build();

        return promotionRepository.save(promotion);
    }
}

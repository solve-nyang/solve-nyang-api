package com.solvenyang.memberPromotion.service;

import com.solvenyang.memberPromotion.domain.Promotion;
import com.solvenyang.memberPromotion.repository.PromotionRepository;
import com.solvenyang.members.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository promotionRepository;

    @Transactional(readOnly = true)
    public Promotion getPromotionByMember(Member member) {
        return promotionRepository.findByMember(member)
                .orElseGet(() -> save(member));
    }

    private Promotion save(Member member) {
        Promotion promotion = Promotion.builder()
                .member(member)
                .build();

        return promotionRepository.save(promotion);
    }
}

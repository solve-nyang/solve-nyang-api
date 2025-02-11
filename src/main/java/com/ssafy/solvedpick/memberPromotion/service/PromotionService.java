package com.ssafy.solvedpick.memberPromotion.service;

import com.ssafy.solvedpick.memberPromotion.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository promotionRepository;


}

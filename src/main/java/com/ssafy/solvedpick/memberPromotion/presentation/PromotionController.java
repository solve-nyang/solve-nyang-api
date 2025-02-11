package com.ssafy.solvedpick.memberPromotion.presentation;

import com.ssafy.solvedpick.memberPromotion.dto.CoinResponseDTO;
import com.ssafy.solvedpick.memberPromotion.facade.PromotionFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/promotion")
public class PromotionController {

    private final PromotionFacade promotionFacade;

    @GetMapping("/member/coin")
    public ResponseEntity<?> getCoin() {
        CoinResponseDTO result = promotionFacade.getMemberCoin();

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/draw")
    public ResponseEntity<?> drawPromotionAvatar() {

        return ResponseEntity.ok().body(null);
    }
}

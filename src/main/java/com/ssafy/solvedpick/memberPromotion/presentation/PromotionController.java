package com.ssafy.solvedpick.memberPromotion.presentation;

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

    @GetMapping()
    public ResponseEntity<?> getCoin() {

        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/draw")
    public ResponseEntity<?> drawPromotionAvatar() {

        return ResponseEntity.ok().body(null);
    }
}

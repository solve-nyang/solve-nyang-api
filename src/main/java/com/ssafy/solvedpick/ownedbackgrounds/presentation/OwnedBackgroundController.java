package com.ssafy.solvedpick.ownedbackgrounds.presentation;

import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.ownedbackgrounds.dto.OwnedBackgroundResponse;
import com.ssafy.solvedpick.ownedbackgrounds.service.OwnedBackgroundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/background/owned")
@RequiredArgsConstructor
public class OwnedBackgroundController {

    private final OwnedBackgroundService ownedBackgroundService;
    private final AuthService authService;

    @GetMapping()
    public ResponseEntity<?> getOwnedBackgrounds() {
        Member member = authService.getCurrentMember();
        OwnedBackgroundResponse response = ownedBackgroundService.getOwnedBackgrounds(member);
        return ResponseEntity.ok(response);
    }
}

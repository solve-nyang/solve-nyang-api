package com.ssafy.solvedpick.ownedbackgrounds.presentation;

import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.composition.service.CompositionService;
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
    private final CompositionService compositionService;

    @GetMapping()
    public ResponseEntity<?> getOwnedBackgrounds() {
        Member member = authService.getCurrentMember();
        OwnedBackgroundResponse response = ownedBackgroundService.getOwnedBackgrounds(member);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{backgroundId}")
    public ResponseEntity<?> updateOwnedBackground(@PathVariable Long backgroundId) {
        Member member = authService.getCurrentMember();
        ownedBackgroundService.updateBackgroundVisibility(member, backgroundId);
        compositionService.invalidateImageCache(member.getUsername());
        return ResponseEntity.noContent().build();
    }
}

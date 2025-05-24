package com.solvenyang.backgrounds.presentation;

import com.solvenyang.auth.service.AuthService;
import com.solvenyang.backgrounds.dto.BackgroundResponse;
import com.solvenyang.backgrounds.service.BackgroundService;
import com.solvenyang.members.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/background")
@RequiredArgsConstructor
public class BackgroundController {

    private final BackgroundService backgroundService;
    private final AuthService authService;

    @GetMapping()
    public ResponseEntity<BackgroundResponse> getAllBackgrounds() {
        Member member = authService.getCurrentMember();
        BackgroundResponse response = backgroundService.getAllBackgroundsWithOwnership(member.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{backgroundId}")
    public ResponseEntity<?> purchaseBackground(@PathVariable Long backgroundId) {
        Member member = authService.getCurrentMember();
        backgroundService.purchaseBackground(backgroundId, member);
        return ResponseEntity.noContent().build();
    }
}

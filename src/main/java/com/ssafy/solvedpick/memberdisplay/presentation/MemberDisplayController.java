package com.ssafy.solvedpick.memberdisplay.presentation;

import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.composition.service.CompositionService;
import com.ssafy.solvedpick.memberdisplay.dto.DisplayTitleRequest;
import com.ssafy.solvedpick.memberdisplay.dto.DisplayVisibilityResponse;
import com.ssafy.solvedpick.memberdisplay.service.MemberDisplayService;
import com.ssafy.solvedpick.members.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/display")
public class MemberDisplayController {

    private final MemberDisplayService memberDisplayService;
    private final AuthService authService;
    private final CompositionService compositionService;

    @GetMapping()
    public ResponseEntity<DisplayVisibilityResponse> getDisplaySetting() {
        Member member = authService.getCurrentMember();
        DisplayVisibilityResponse response = memberDisplayService.getDisplaySetting(member);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/user-title")
    public ResponseEntity<?> setDisplayTitle(@RequestBody DisplayTitleRequest displayTitleRequest) {
        Member member = authService.getCurrentMember();
        memberDisplayService.setDisplayTitle(member, displayTitleRequest);
        compositionService.invalidateImageCache(member.getUsername());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/tier")
    public ResponseEntity<?> toggleTier() {
        Member member = authService.getCurrentMember();
        memberDisplayService.toggleTier(member);
        compositionService.invalidateImageCache(member.getUsername());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/streak")
    public ResponseEntity<?> toggleStreak() {
        Member member = authService.getCurrentMember();
        memberDisplayService.toggleStreak(member);
        compositionService.invalidateImageCache(member.getUsername());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/class")
    public ResponseEntity<?> toggleMemberClass() {
        Member member = authService.getCurrentMember();
        memberDisplayService.toggleMemberClass(member);
        compositionService.invalidateImageCache(member.getUsername());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/title")
    public ResponseEntity<?> toggleTitle() {
        Member member = authService.getCurrentMember();
        memberDisplayService.toggleTitle(member);
        compositionService.invalidateImageCache(member.getUsername());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/solved")
    public ResponseEntity<?> toggleSolvedCount() {
        Member member = authService.getCurrentMember();
        memberDisplayService.toggleSolvedCount(member);
        compositionService.invalidateImageCache(member.getUsername());
        return ResponseEntity.noContent().build();
    }

}

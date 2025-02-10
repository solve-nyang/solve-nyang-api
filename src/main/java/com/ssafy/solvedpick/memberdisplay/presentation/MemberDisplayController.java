package com.ssafy.solvedpick.memberdisplay.presentation;

import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.memberdisplay.dto.DisplayVisibilityResponse;
import com.ssafy.solvedpick.memberdisplay.service.MemberDisplayService;
import com.ssafy.solvedpick.members.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/display")
public class MemberDisplayController {

    private final MemberDisplayService memberDisplayService;
    private final AuthService authService;

    @GetMapping()
    public ResponseEntity<DisplayVisibilityResponse> getDisplaySetting() {
        Member member = authService.getCurrentMember();
        DisplayVisibilityResponse response = memberDisplayService.getDisplaySetting(member);
        return ResponseEntity.ok(response);
    }


}

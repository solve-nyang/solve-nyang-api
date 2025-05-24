package com.solvenyang.members.presentation;

import com.solvenyang.auth.service.AuthService;
import com.solvenyang.members.facade.UserFacade;
import com.solvenyang.members.domain.Member;
import com.solvenyang.members.dto.BasicUsernameResponse;
import com.solvenyang.members.dto.UserPointResponse;
import com.solvenyang.members.dto.UserProfileResponse;
import com.solvenyang.members.service.MemberService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/me")
public class MemberController {

    private final MemberService memberService;
    private final AuthService authService;
    private final UserFacade userFacade;

    @GetMapping()
    public ResponseEntity<BasicUsernameResponse> getUsername(){
        Member member = authService.getCurrentMember();
        BasicUsernameResponse response = memberService.getUsername(member);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getUserProfile(){
        Member member = authService.getCurrentMember();
        UserProfileResponse response = userFacade.getUserProfile(member);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/point")
    public ResponseEntity<UserPointResponse> getUserPoint(){
        Member member = authService.getCurrentMember();
        UserPointResponse response = memberService.getUserPoint(member);
        return ResponseEntity.ok(response);
    }
}

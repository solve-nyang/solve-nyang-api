package com.ssafy.solvedpick.members.presentation;

import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.members.service.MemberService;
import com.ssafy.solvedpick.members.dto.UserInfoResponse;
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

    @GetMapping()
    public ResponseEntity<UserInfoResponse> getUserInfo(){
        Member member = authService.getCurrentMember();
        UserInfoResponse result = memberService.getUserInfo(member);
        return ResponseEntity.ok(result);
    }
}

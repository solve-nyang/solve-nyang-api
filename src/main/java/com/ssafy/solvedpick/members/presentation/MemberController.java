package com.ssafy.solvedpick.members.presentation;

import com.ssafy.solvedpick.api.dto.UserInfoApiResponse;
import com.ssafy.solvedpick.members.service.MemberService;
import com.ssafy.solvedpick.members.dto.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/me")
public class MemberController {

    private final MemberService memberService;

    @GetMapping()
    public ResponseEntity<UserInfoResponse> getUSerInfo(@RequestHeader(value="Authorization") Long memberId){

        UserInfoResponse result = memberService.getUserInfo(memberId);
        return ResponseEntity.ok(result);
    }
}
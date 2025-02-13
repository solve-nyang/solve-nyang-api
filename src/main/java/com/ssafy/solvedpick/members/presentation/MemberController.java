package com.ssafy.solvedpick.members.presentation;

import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.facade.UserFacade;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.members.dto.BasicUsernameResponse;
import com.ssafy.solvedpick.members.dto.UserPointResponse;
import com.ssafy.solvedpick.members.dto.UserProfileResponse;
import com.ssafy.solvedpick.members.repository.MemberRepository;
import com.ssafy.solvedpick.members.service.MemberService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/me")
public class MemberController {

    private final MemberService memberService;
    private final AuthService authService;
    private final MemberRepository memberRepository;
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
        UserProfileResponse response = memberService.getUserProfile(member);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/point")
    public ResponseEntity<UserPointResponse> getUserPoint(){
        Member member = authService.getCurrentMember();
        UserPointResponse response = memberService.getUserPoint(member);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<?> syncMember(){
        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            System.out.println(member.getUsername());
            userFacade.syncUserInfo(member);
        }
        return ResponseEntity.ok().build();
    }
}

package com.ssafy.solvedpick.members.scheduler;

import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.members.repository.MemberRepository;
import com.ssafy.solvedpick.members.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserInfoScheduler {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Scheduled(cron = "0 0 4 * * *")
    public void updateUserInfo() {
        List<Member> members = memberRepository.findAll();

        for (Member member : members) {
            memberService.updateUserProcess(member);
        }
    }
}
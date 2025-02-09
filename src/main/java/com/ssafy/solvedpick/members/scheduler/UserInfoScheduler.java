package com.ssafy.solvedpick.members.scheduler;

import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.members.repository.MemberRepository;
import com.ssafy.solvedpick.members.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserInfoScheduler {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Scheduled(cron = "0 20 23 * * *")
    public void updateUserInfo() {
        List<Member> members = memberRepository.findAll();

        log.debug("updateUser Start(List size) : {}", members.size());
        for (Member member : members) {
            memberService.updateUserProcess(member);
        }
        log.debug("updateUser End(List size) : {}", members.size());
    }
}
package com.ssafy.solvedpick.members.scheduler;

import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.members.repository.MemberRepository;
import com.ssafy.solvedpick.members.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserInfoScheduler {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Transactional
    @Scheduled(cron = "0 0 12 * * *", zone="Asia/Seoul")
    public void updateUserInfo() {
        List<Member> members = memberRepository.findAll();
        log.info("scheduled update start, size: {}", members.size());

        for (Member member : members) {
            try {
                memberService.updateUserProcess(member);
            } catch (Exception e) {
                log.info("stopped at {}", member.getId());
            }
        }

        log.info("updateUser End(List size)");
    }
}
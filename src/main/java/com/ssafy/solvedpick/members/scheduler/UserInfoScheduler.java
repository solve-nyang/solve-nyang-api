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

    @Scheduled(cron = "0 40 13 * * *", zone="Asia/Seoul")
    public void updateUserInfo() {
        List<Long> memberIds = memberRepository.findAllIds();  // ID만 조회
        log.info("scheduled update start, size: {}", memberIds.size());

        int batchSize = 100;
        for (int i = 0; i < memberIds.size(); i += batchSize) {
            int end = Math.min(i + batchSize, memberIds.size());
            List<Long> batchIds = memberIds.subList(i, end);

            for (Long memberId : batchIds) {
                try {
                    memberService.updateUserProcess(memberId);
                    Thread.sleep(200);
                } catch (Exception e) {
                    log.error("Failed to update user: {}", memberId, e);
                }
            }

            log.info("Processed batch from {} to {}", i, end);
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        log.info("updateUser End");
    }
}
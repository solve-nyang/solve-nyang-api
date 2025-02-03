package com.ssafy.solvedpick.auth.scheduler;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ssafy.solvedpick.auth.repository.VerificationKeyRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class VerificationKeyScheduler {
	private final VerificationKeyRepository verificationKeyRepository;

	@Transactional
	@Scheduled(cron = "0 0 4 * * *")
	public void removeExpiredKeys() {
	LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
	verificationKeyRepository.deleteByCreatedAtBefore(tenMinutesAgo);
	}
}

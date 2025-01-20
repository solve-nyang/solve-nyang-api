package com.ssafy.solvedpick.accounts.scheduler;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ssafy.solvedpick.accounts.repository.VerificationKeyRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VerificationKeyScheduler {
	private final VerificationKeyRepository verificationKeyRepository;
	    
	@Scheduled(cron = "0 0 4 * * *")
	public void removeExpiredKeys() {
	LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
	verificationKeyRepository.deleteByCreatedAtBefore(tenMinutesAgo);
	}
}

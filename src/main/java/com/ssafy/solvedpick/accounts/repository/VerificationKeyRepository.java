package com.ssafy.solvedpick.accounts.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.solvedpick.accounts.domain.VerificationKey;

public interface VerificationKeyRepository extends JpaRepository<VerificationKey, Long>{

	String findByVerificationCode(String username);
	VerificationKey findByUsername(String username);
    void deleteByCreatedAtBefore(LocalDateTime dateTime);

}

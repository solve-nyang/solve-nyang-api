package com.ssafy.solvedpick.accounts.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.solvedpick.accounts.domain.VerificationKey;

public interface VerificationKeyRepository extends JpaRepository<VerificationKey, Long>{

	Optional<String> findByVerificationCode(String username);
	Optional<VerificationKey> findByUsername(String username);
	void deleteByCreatedAtBefore(LocalDateTime dateTime);

}

package com.ssafy.solvedpick.accounts.repository;

import com.ssafy.solvedpick.accounts.domain.Member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>{
	boolean existsByUsername(String username);
	Optional<Member> findByUsername(String username);
	Member findById(long l);
}

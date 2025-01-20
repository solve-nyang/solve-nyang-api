package com.ssafy.solvedpick.members.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.solvedpick.members.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	boolean existsByUsername(String username);
	Optional<Member> findByUsername(String username);
	Member findById(long l);
}

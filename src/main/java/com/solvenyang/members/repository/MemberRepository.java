package com.solvenyang.members.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solvenyang.members.domain.Member;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long>{
	boolean existsByUsername(String username);
	Optional<Member> findByUsername(String username);
	Member findById(long l);
	@Query("SELECT m.id FROM Member m")
	List<Long> findAllIds();
}

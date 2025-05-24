package com.solvenyang.problem.repository;

import com.solvenyang.members.domain.Member;
import com.solvenyang.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    Optional<Problem> findByMember(Member member);
}

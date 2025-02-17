package com.ssafy.solvedpick.problem.repository;

import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    Optional<Problem> findByMember(Member member);
}

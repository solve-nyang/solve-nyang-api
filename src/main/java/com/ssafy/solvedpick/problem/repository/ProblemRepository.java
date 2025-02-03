package com.ssafy.solvedpick.problem.repository;

import com.ssafy.solvedpick.problem.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
}

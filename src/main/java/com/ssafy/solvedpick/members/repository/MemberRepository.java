package com.ssafy.solvedpick.members.repository;

import com.ssafy.solvedpick.members.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findById(long l);
}

package com.ssafy.solvedpick.memberdisplay.repository;

import com.ssafy.solvedpick.memberdisplay.domain.MemberDisplay;
import com.ssafy.solvedpick.members.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberDisplayRepository extends JpaRepository<MemberDisplay, Long> {

    Optional<MemberDisplay> findByMember(Member member);
}

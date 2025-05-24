package com.solvenyang.memberdisplay.repository;

import com.solvenyang.memberdisplay.domain.MemberDisplay;
import com.solvenyang.members.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberDisplayRepository extends JpaRepository<MemberDisplay, Long> {

    Optional<MemberDisplay> findByMember(Member member);
}

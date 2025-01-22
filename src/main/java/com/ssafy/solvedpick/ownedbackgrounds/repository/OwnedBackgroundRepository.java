package com.ssafy.solvedpick.ownedbackgrounds.repository;

import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.ownedbackgrounds.domain.OwnedBackground;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OwnedBackgroundRepository extends JpaRepository<OwnedBackground, Long> {

    Optional<OwnedBackground> findByMemberAndVisibleTrue(Member member);

}

package com.ssafy.solvedpick.ownedbackgrounds.repository;

import com.ssafy.solvedpick.backgrounds.domain.Background;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.ownedbackgrounds.domain.OwnedBackground;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OwnedBackgroundRepository extends JpaRepository<OwnedBackground, Long> {

    Optional<OwnedBackground> findByMemberAndVisibleTrue(Member member);

    List<OwnedBackground> findAllByMember(Member member);

    Boolean existsByMemberAndBackground(Member member, Background background);
}

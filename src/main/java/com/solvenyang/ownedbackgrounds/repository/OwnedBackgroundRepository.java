package com.solvenyang.ownedbackgrounds.repository;

import com.solvenyang.backgrounds.domain.Background;
import com.solvenyang.members.domain.Member;
import com.solvenyang.ownedbackgrounds.domain.OwnedBackground;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OwnedBackgroundRepository extends JpaRepository<OwnedBackground, Long> {

    Optional<OwnedBackground> findByMemberAndVisibleTrue(Member member);

    List<OwnedBackground> findAllByMember(Member member);

    Boolean existsByMemberAndBackground(Member member, Background background);

    Optional<OwnedBackground> findByIdAndMember(Long id, Member member);
}

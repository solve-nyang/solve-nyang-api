package com.ssafy.solvedpick.memberPromotion.repository;

import com.ssafy.solvedpick.memberPromotion.domain.Promotion;
import com.ssafy.solvedpick.members.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    Optional<Promotion> findByMember(Member member);
}

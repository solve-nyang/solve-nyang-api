package com.solvenyang.memberPromotion.repository;

import com.solvenyang.memberPromotion.domain.Promotion;
import com.solvenyang.members.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    Optional<Promotion> findByMember(Member member);
}

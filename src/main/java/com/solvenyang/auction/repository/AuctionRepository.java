package com.solvenyang.auction.repository;

import com.solvenyang.auction.domain.Auction;
import com.solvenyang.members.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar", "ownedAvatar.member"})
    Page<Auction> findAllByOwnedAvatar_Avatar_KoreanNameContainingAndSoldAndCancelledFalse(
            String keyword, Boolean sold, Pageable pageable
    );

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar", "ownedAvatar.member"})
    Page<Auction> findAllBySoldAndCancelledFalse(Boolean sold, Pageable pageable);

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar", "ownedAvatar.member"})
    Page<Auction> findAllByOwnedAvatar_Avatar_KoreanNameContainingAndSoldAndOwnedAvatar_Avatar_GradeAndCancelledFalse(
            String keyword, Boolean sold, Integer grade, Pageable pageable
    );

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar", "ownedAvatar.member"})
    Page<Auction> findAllByOwnedAvatar_Avatar_GradeAndSoldAndCancelledFalse(
            Integer grade, Boolean sold, Pageable pageable
    );

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar", "ownedAvatar.member"})
    Optional<Auction> findById(Long id);

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar", "ownedAvatar.member"})
    Optional<Auction> findByIdAndOwnedAvatar_Member(Long id, Member member);

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar", "ownedAvatar.member"})
    Page<Auction> findAllByOwnedAvatar_Member(Member member, Pageable pageable);

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar", "ownedAvatar.member"})
    Page<Auction> findAllByOwnedAvatar_MemberAndSoldTrue(Member member, Pageable pageable);

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar", "ownedAvatar.member"})
    Page<Auction> findAllByOwnedAvatar_MemberAndCancelledTrue(Member member, Pageable pageable);

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar", "ownedAvatar.member"})
    Page<Auction> findAllByOwnedAvatar_MemberAndSoldFalseAndCancelledFalse(Member member, Pageable pageable);
}

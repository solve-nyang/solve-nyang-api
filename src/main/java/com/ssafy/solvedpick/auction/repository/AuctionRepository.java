package com.ssafy.solvedpick.auction.repository;

import com.ssafy.solvedpick.auction.domain.Auction;
import com.ssafy.solvedpick.members.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar", "ownedAvatar.member"})
    Page<Auction> findAllByOwnedAvatar_Avatar_NameContainingAndCancelledFalse(
            String keyword, Pageable pageable
    );

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar", "ownedAvatar.member"})
    Page<Auction> findAllByCancelledFalse(Pageable pageable);

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar", "ownedAvatar.member"})
    Page<Auction> findAllByOwnedAvatar_Avatar_NameContainingAndOwnedAvatar_Avatar_GradeAndCancelledFalse(
            String keyword, Integer grade, Pageable pageable
    );

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar", "ownedAvatar.member"})
    Page<Auction> findAllByOwnedAvatar_Avatar_GradeAndCancelledFalse(Integer grade, Pageable pageable);

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar", "ownedAvatar.member"})
    Optional<Auction> findByIdAndSoldFalseAndCancelledFalse(Long id);

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar", "ownedAvatar.member"})
    Page<Auction> findAllByOwnedAvatar_Member(Member member, Pageable pageable);

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar", "ownedAvatar.member"})
    Page<Auction> findAllByOwnedAvatar_MemberAndSoldTrue(Member member, Pageable pageable);

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar", "ownedAvatar.member"})
    Page<Auction> findAllByOwnedAvatar_MemberAndCancelledTrue(Member member, Pageable pageable);

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar", "ownedAvatar.member"})
    Page<Auction> findAllByOwnedAvatar_MemberAndSoldFalseAndCancelledFalse(Member member, Pageable pageable);
}

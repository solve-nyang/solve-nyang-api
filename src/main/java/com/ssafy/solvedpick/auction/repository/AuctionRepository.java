package com.ssafy.solvedpick.auction.repository;

import com.ssafy.solvedpick.auction.domain.Auction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar"})
    Page<Auction> findAllByOwnedAvatar_Avatar_NameContainingAndSoldFalseAndCancelledFalse(
            String keyword, Pageable pageable);

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar"})
    Page<Auction> findAllBySoldFalseAndCancelledFalse(Pageable pageable);

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar"})
    Page<Auction> findAllByOwnedAvatar_Avatar_NameContainingAndOwnedAvatar_Avatar_GradeAndSoldFalseAndCancelledFalse(
            int grade, String keyword, Pageable pageable);

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar"})
    Page<Auction> findAllByOwnedAvatar_Avatar_GradeAndSoldFalseAndCancelledFalse(int grade, Pageable pageable);
}

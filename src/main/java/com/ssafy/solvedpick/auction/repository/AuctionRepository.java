package com.ssafy.solvedpick.auction.repository;

import com.ssafy.solvedpick.auction.domain.Auction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar"})
    Page<Auction> findAllByOwnedAvatar_Avatar_NameContainingAndSoldFalseAndCancelledFalse(
            String keyword, Pageable pageable
    );

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar"})
    Page<Auction> findAllBySoldFalseAndCancelledFalse(Pageable pageable);

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar"})
    Page<Auction> findAllByOwnedAvatar_Avatar_NameContainingAndOwnedAvatar_Avatar_GradeAndSoldFalseAndCancelledFalse(
            String keyword, int grade, Pageable pageable
    );

    @EntityGraph(attributePaths = {"ownedAvatar", "ownedAvatar.avatar"})
    Page<Auction> findAllByOwnedAvatar_Avatar_GradeAndSoldFalseAndCancelledFalse(int grade, Pageable pageable);

    @Query("SELECT a " +
            "FROM Auction a " +
            "LEFT JOIN FETCH a.ownedAvatar " +
            "LEFT JOIN FETCH a.ownedAvatar.member " +
            "WHERE a.ownedAvatar.member.id = :memberId " +
            "ORDER BY a.createdAt DESC")
    List<Auction> findAllByMember(@Param("memberId") Long memberId);
}

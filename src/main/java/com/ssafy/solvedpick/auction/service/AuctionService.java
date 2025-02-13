package com.ssafy.solvedpick.auction.service;

import com.ssafy.solvedpick.auction.domain.Auction;
import com.ssafy.solvedpick.auction.repository.AuctionRepository;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.ownedavatar.domain.OwnedAvatar;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;

    @Transactional(readOnly = true)
    public Page<Auction> findMerchandiseWithKeyword(String keyword, Boolean sold, Pageable pageable) {
        return auctionRepository.findAllByOwnedAvatar_Avatar_KoreanNameContainingAndSoldAndCancelledFalse(
                keyword, sold, pageable
        );
    }

    @Transactional(readOnly = true)
    public Page<Auction> findMerchandise(Boolean sold, Pageable pageable) {
        return auctionRepository.findAllBySoldAndCancelledFalse(sold, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Auction> findMerchandiseWithKeywordAndGrade(
            String keyword, Boolean sold, int grade, Pageable pageable
    ) {
        return auctionRepository
                .findAllByOwnedAvatar_Avatar_KoreanNameContainingAndSoldAndOwnedAvatar_Avatar_GradeAndCancelledFalse(
                        keyword, sold, grade, pageable
                );
    }

    @Transactional(readOnly = true)
    public Page<Auction> findMerchandiseWithGrade(int grade, Boolean sold, Pageable pageable) {
        return auctionRepository.findAllByOwnedAvatar_Avatar_GradeAndSoldAndCancelledFalse(grade, sold, pageable);
    }

    public Auction cancelAuction(Long auctionId, Member member) {
        Auction auction = auctionRepository.findByIdAndOwnedAvatar_MemberAndSoldFalseAndCancelledFalse(
                        auctionId, member
                )
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        auction.cancel();
        return auction;
    }

    public Auction buyAvatar(Long id) {
        Auction auction = auctionRepository.findByIdAndSoldFalseAndCancelledFalse(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        auction.sold();

        return auction;
    }

    @Transactional(readOnly = true)
    public Page<Auction> findMemberHistoryAll(Member member, Pageable pageable) {
        return auctionRepository.findAllByOwnedAvatar_Member(member, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Auction> findMemberHistorySold(Member member, Pageable pageable) {
        return auctionRepository.findAllByOwnedAvatar_MemberAndSoldTrue(member, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Auction> findMemberHistoryCancelled(Member member, Pageable pageable) {
        return auctionRepository.findAllByOwnedAvatar_MemberAndCancelledTrue(member, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Auction> findMemberHistoryOnStatus(Member member, Pageable pageable) {
        return auctionRepository.findAllByOwnedAvatar_MemberAndSoldFalseAndCancelledFalse(member, pageable);
    }

    public void createAuction(OwnedAvatar ownedAvatar, Long price) {
        Auction auction = Auction.builder()
                .price(price)
                .ownedAvatar(ownedAvatar)
                .build();

        auctionRepository.save(auction);
    }
}

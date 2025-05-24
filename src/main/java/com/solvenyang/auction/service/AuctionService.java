package com.solvenyang.auction.service;

import com.solvenyang.auction.domain.Auction;
import com.solvenyang.auction.repository.AuctionRepository;
import com.solvenyang.members.domain.Member;
import com.solvenyang.ownedavatar.domain.OwnedAvatar;
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
        Auction auction = auctionRepository.findByIdAndOwnedAvatar_Member(
                        auctionId, member
                )
                .orElseThrow(() -> new HttpClientErrorException(
                        HttpStatus.BAD_REQUEST,
                        "존재하지 않는 아바타입니다."
                ));
        validateAuctionAvailable(auction);
        auction.cancel();

        return auction;
    }

    public Auction buyAvatar(Long id) {
        Auction auction = auctionRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(
                        HttpStatus.BAD_REQUEST,
                        "존재하지 않는 아바타입니다."
                ));
        validateAuctionAvailable(auction);
        auction.sold();

        return auction;
    }

    private void validateAuctionAvailable(Auction auction) {
        if (auction.getSold()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "이미 판매된 아바타입니다.");
        }

        if (auction.getCancelled()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "이미 취소된 아바타입니다.");
        }
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

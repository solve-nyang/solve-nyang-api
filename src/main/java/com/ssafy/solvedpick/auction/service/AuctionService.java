package com.ssafy.solvedpick.auction.service;

import com.ssafy.solvedpick.auction.domain.Auction;
import com.ssafy.solvedpick.auction.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;

    @Transactional(readOnly = true)
    public Page<Auction> findMerchandiseWithKeyword(String keyword, Pageable pageable) {
        return auctionRepository.findAllByOwnedAvatar_Avatar_NameContainingAndSoldFalseAndCancelledFalse(
                keyword, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Auction> findMerchandise(Pageable pageable) {
        return auctionRepository.findAllBySoldFalseAndCancelledFalse(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Auction> findMerchandiseWithKeywordAndGrade(String keyword, int grade, Pageable pageable) {
        return auctionRepository
                .findAllByOwnedAvatar_Avatar_NameContainingAndOwnedAvatar_Avatar_GradeAndSoldFalseAndCancelledFalse(
                grade, keyword, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Auction> findMerchandiseWithGrade(int grade, Pageable pageable) {
        return auctionRepository.findAllByOwnedAvatar_Avatar_GradeAndSoldFalseAndCancelledFalse(grade, pageable);
    }

    public void save(Auction auction) {
        auctionRepository.save(auction);
    }
}

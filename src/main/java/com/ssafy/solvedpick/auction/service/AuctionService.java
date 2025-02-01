package com.ssafy.solvedpick.auction.service;

import com.ssafy.solvedpick.auction.domain.Auction;
import com.ssafy.solvedpick.auction.repository.AuctionRepository;
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
    public Page<Auction> findMerchandiseWithKeyword(String keyword, Pageable pageable) {
        return auctionRepository.findAllByOwnedAvatar_Avatar_NameContainingAndSoldFalseAndCancelledFalse(
                keyword, pageable
        );
    }

    @Transactional(readOnly = true)
    public Page<Auction> findMerchandise(Pageable pageable) {
        return auctionRepository.findAllBySoldFalseAndCancelledFalse(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Auction> findMerchandiseWithKeywordAndGrade(String keyword, int grade, Pageable pageable) {
        return auctionRepository
                .findAllByOwnedAvatar_Avatar_NameContainingAndOwnedAvatar_Avatar_GradeAndSoldFalseAndCancelledFalse(
                        keyword, grade, pageable
                );
    }

    @Transactional(readOnly = true)
    public Page<Auction> findMerchandiseWithGrade(int grade, Pageable pageable) {
        return auctionRepository.findAllByOwnedAvatar_Avatar_GradeAndSoldFalseAndCancelledFalse(grade, pageable);
    }

    public void save(Auction auction) {
        auctionRepository.save(auction);
    }

    public Auction cancelAuction(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        auction.cancel();
        return auction;
    }
}

package com.ssafy.solvedpick.auction.service;

import com.ssafy.solvedpick.auction.domain.Auction;
import com.ssafy.solvedpick.auction.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;

    public Page<Auction> findMerchandiseWithKeyword(String keyword, Pageable pageable) {
        return auctionRepository.findAllByOwnedAvatar_Avatar_NameContainingAndSoldFalseAndCancelledFalse(
                keyword, pageable);
    }

    public Page<Auction> findMerchandise(Pageable pageable) {
        return auctionRepository.findAllBySoldFalseAndCancelledFalse(pageable);
    }
}

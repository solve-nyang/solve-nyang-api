package com.ssafy.solvedpick.auction.facade;

import com.ssafy.solvedpick.auction.domain.Auction;
import com.ssafy.solvedpick.auction.enums.FilterType;
import com.ssafy.solvedpick.auction.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuctionFacade {

    private static final int PAGE_SIZE = 10;

    private final AuctionService auctionService;

    public void searchMerchandise(String keyword, int filter, int page) {
        Sort sort = FilterType.fromValue(filter);
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE, sort);

        Page<Auction> result = Optional.ofNullable(keyword)
                .map(k -> auctionService.findMerchandiseWithKeyword(k, pageable))
                .orElseGet(() -> auctionService.findMerchandise(pageable));
        // TODO: Convert to Response DTO
    }
}

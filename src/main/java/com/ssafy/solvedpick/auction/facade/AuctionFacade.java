package com.ssafy.solvedpick.auction.facade;

import com.ssafy.solvedpick.auction.domain.Auction;
import com.ssafy.solvedpick.auction.dto.AuctionMerchandiseDTO;
import com.ssafy.solvedpick.auction.dto.SearchMerchandiseResponseDTO;
import com.ssafy.solvedpick.auction.enums.SortType;
import com.ssafy.solvedpick.auction.service.AuctionService;
import com.ssafy.solvedpick.common.utils.grade.Grade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuctionFacade {

    private static final int PAGE_SIZE = 10;
    private static final int NO_GRADE = -1;

    private final AuctionService auctionService;

    @Transactional
    public SearchMerchandiseResponseDTO searchMerchandise(String keyword, String rarity, int order, int page) {
        Sort sort = SortType.fromValue(order);
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE, sort);

        int grade = Optional.ofNullable(rarity)
                .map(Grade::getValueFromName)
                .orElseGet(() -> NO_GRADE);

        Page<Auction> result = findAuctions(keyword, grade, pageable);

        return SearchMerchandiseResponseDTO.builder()
                .size(result.getSize())
                .hasNext(result.hasNext())
                .totalPage(result.getTotalPages())
                .hasPrevious(result.hasPrevious())
                .currentPageNumber(result.getNumber() + 1)
                .merchandises(result.getContent()
                        .stream()
                        .map(auction -> AuctionMerchandiseDTO.builder()
                                .id(auction.getId())
                                .price(auction.getPrice())
                                .createdAt(auction.getCreatedAt())
                                .name(auction.getOwnedAvatar().getAvatar().getName())
                                .rarity(Grade.fromValue(auction.getOwnedAvatar().getAvatar().getGrade()).name())
                                .build())
                        .toList())
                .build();
    }

    private Page<Auction> findAuctions(String keyword, int grade, Pageable pageable) {
        if (grade == NO_GRADE) {
            return Optional.ofNullable(keyword)
                    .map(k -> auctionService.findMerchandiseWithKeyword(k, pageable))
                    .orElseGet(() -> auctionService.findMerchandise(pageable));
        }

        return Optional.ofNullable(keyword)
                .map(k -> auctionService.findMerchandiseWithKeywordAndGrade(k, grade, pageable))
                .orElseGet(() -> auctionService.findMerchandiseWithGrade(grade, pageable));
    }
}

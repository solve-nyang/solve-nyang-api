package com.ssafy.solvedpick.auction.facade;

import com.ssafy.solvedpick.auction.domain.Auction;
import com.ssafy.solvedpick.auction.dto.*;
import com.ssafy.solvedpick.auction.enums.HistoryFilterType;
import com.ssafy.solvedpick.auction.enums.SortType;
import com.ssafy.solvedpick.auction.service.AuctionService;
import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.common.dto.ResponseMessageDTO;
import com.ssafy.solvedpick.common.utils.grade.Grade;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.members.service.MemberService;
import com.ssafy.solvedpick.ownedavatar.domain.OwnedAvatar;
import com.ssafy.solvedpick.ownedavatar.service.OwnedAvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionFacade {

    private static final int PAGE_SIZE = 10;
    private static final int NO_GRADE = -1;

    private final AuctionService auctionService;
    private final AuthService authService;
    private final OwnedAvatarService ownedAvatarService;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public SearchMerchandiseResponseDTO searchMerchandise(
            String keyword, String rarity, int order, int page, Boolean sold
    ) {
        Member member = authService.getCurrentMember();
        Sort sort = SortType.fromValue(order);
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE, sort);

        int grade = Optional.ofNullable(rarity)
                .map(Grade::getValueFromName)
                .orElse(NO_GRADE);

        Page<Auction> result = findAuctionsWithCondition(keyword, grade, sold, pageable);

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
                                .sold(auction.getSold())
                                .price(auction.getPrice())
                                .createdAt(auction.getCreatedAt())
                                .name(auction.getOwnedAvatar().getAvatar().getName())
                                .isMine(member.getId().equals(auction.getOwnedAvatar().getMember().getId()))
                                .rarity(Grade.fromValue(auction.getOwnedAvatar().getAvatar().getGrade()).name())
                                .build())
                        .toList())
                .build();
    }

    private Page<Auction> findAuctionsWithCondition(String keyword, int grade, Boolean sold, Pageable pageable) {
        if (grade == NO_GRADE) {
            return Optional.ofNullable(keyword)
                    .map(k -> auctionService.findMerchandiseWithKeyword(k, sold, pageable))
                    .orElseGet(() -> auctionService.findMerchandise(sold, pageable));
        }

        return Optional.ofNullable(keyword)
                .map(k -> auctionService.findMerchandiseWithKeywordAndGrade(k, sold, grade, pageable))
                .orElseGet(() -> auctionService.findMerchandiseWithGrade(grade, sold, pageable));
    }

    public ResponseMessageDTO sellAvatar(SellAvatarRequestDTO requestDTO) {
        Member currentMember = authService.getCurrentMember();
        OwnedAvatar ownedAvatar = ownedAvatarService.sellToAuction(requestDTO.getId(), currentMember);
        auctionService.createAuction(ownedAvatar, requestDTO.getPrice());

        return ResponseMessageDTO.builder()
                .message("success")
                .build();
    }

    public void cancelSale(AuctionCancelRequestDTO requestDTO) {
        Auction auction = auctionService.cancelAuction(requestDTO.getId());
        ownedAvatarService.cancelSold(auction.getOwnedAvatar());
    }

    @Transactional(readOnly = true)
    public SalesHistoryResponseDTO getSalesHistory(int filter, int page) {
        Member member = authService.getCurrentMember();
        Sort sort = SortType.NEWEST.getQuery();
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE, sort);
        Page<Auction> result = getMemberHistoryWithCondition(filter, member, pageable);

        return SalesHistoryResponseDTO.builder()
                .size(result.getSize())
                .hasNext(result.hasNext())
                .totalPage(result.getTotalPages())
                .hasPrevious(result.hasPrevious())
                .currentPageNumber(result.getNumber() + 1)
                .history(result.getContent()
                        .stream()
                        .map(auction -> MemberAuctionDTO.builder()
                                .id(auction.getId())
                                .sold(auction.getSold())
                                .price(auction.getPrice())
                                .createdAt(auction.getCreatedAt())
                                .cancelled(auction.getCancelled())
                                .name(auction.getOwnedAvatar().getAvatar().getName())
                                .rarity(Grade.fromValue(auction.getOwnedAvatar().getAvatar().getGrade()).name())
                                .build())
                        .toList())
                .build();
    }

    private Page<Auction> getMemberHistoryWithCondition(int filter, Member member, Pageable pageable) {
        if (filter == HistoryFilterType.ALL.getValue()) {
            return auctionService.findMemberHistoryAll(member, pageable);
        } else if (filter == HistoryFilterType.SOLD.getValue()) {
            return auctionService.findMemberHistorySold(member, pageable);
        } else if (filter == HistoryFilterType.ON_STATUS.getValue()) {
            return auctionService.findMemberHistoryOnStatus(member, pageable);
        } else if (filter == HistoryFilterType.CANCELLED.getValue()) {
            return auctionService.findMemberHistoryCancelled(member, pageable);
        }

        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
    }

    public void buyAvatar(AuctionBuyRequestDTO requestDTO) {
        Member buyer = authService.getCurrentMember();
        Auction auction = auctionService.buyAvatar(requestDTO.getId());
        Member seller = auction.getOwnedAvatar().getMember();

        if (seller.getId().equals(buyer.getId())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        memberService.sellAvatar(seller, auction.getPrice());
        memberService.buyAvatar(buyer, auction.getPrice());
        ownedAvatarService.buyAvatar(buyer, auction.getOwnedAvatar().getAvatar());
    }
}

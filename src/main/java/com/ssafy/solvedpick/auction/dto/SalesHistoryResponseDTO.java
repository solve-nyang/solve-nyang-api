package com.ssafy.solvedpick.auction.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SalesHistoryResponseDTO {

    private Integer currentPageNumber;
    private Integer size;
    private Integer totalPage;
    private Boolean hasNext;
    private Boolean hasPrevious;
    private List<MemberAuctionDTO> history;
}

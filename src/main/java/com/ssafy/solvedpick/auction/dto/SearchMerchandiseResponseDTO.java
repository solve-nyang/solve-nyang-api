package com.ssafy.solvedpick.auction.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchMerchandiseResponseDTO {

    private Integer currentPageNumber;
    private Integer size;
    private Integer totalPage;
    private Boolean hasNext;
    private Boolean hasPrevious;

    // TODO: ADD List of a merchandise information
}

package com.solvenyang.auction.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAuctionDTO {

    private Long id;
    private Long price;
    private String name;
    private String rarity;
    private Boolean sold;
    private Boolean cancelled;
    private LocalDateTime createdAt;
}

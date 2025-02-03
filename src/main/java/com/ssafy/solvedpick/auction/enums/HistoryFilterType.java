package com.ssafy.solvedpick.auction.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum HistoryFilterType {

    ALL(0),
    SOLD(1),
    ON_STATUS(2),
    CANCELLED(3);

    private final int value;
}

package com.ssafy.solvedpick.auction.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum FilterType {

    NEWEST(0, Sort.by(Sort.Direction.DESC,"created_at")),
    OLDEST(1, Sort.by(Sort.Direction.ASC,"created_at")),
    PRICE_HIGH(3, Sort.by(Sort.Direction.DESC,"price")),
    PRICE_LOW(2, Sort.by(Sort.Direction.ASC,"price"));

    private final int value;
    private final Sort query;

    public static Sort fromValue(int value) {
        return Arrays.stream(values())
                .filter(filter -> filter.value == value)
                .findFirst()
                .map(filter -> filter.query)
                .orElseThrow(IllegalArgumentException::new);
    }
}

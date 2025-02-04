package com.ssafy.solvedpick.backgrounds.service;

import com.ssafy.solvedpick.backgrounds.dto.BackgroundInfo;
import com.ssafy.solvedpick.backgrounds.dto.BackgroundQueryResult;
import com.ssafy.solvedpick.backgrounds.dto.BackgroundResponse;
import com.ssafy.solvedpick.backgrounds.repository.BackgroundRepository;
import com.ssafy.solvedpick.common.enums.BackgroundType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BackgroundService {

    private final BackgroundRepository backgroundRepository;

    @Transactional(readOnly = true)
    public BackgroundResponse getAllBackgroundsWithOwnership(Long memberId) {
        List<BackgroundQueryResult> queryResults = backgroundRepository.findAllWithOwnership(memberId);

        List<BackgroundInfo> backgroundInfos = queryResults.stream()
                .map(result -> BackgroundInfo.builder()
                        .name(result.getName())
                        .owned(result.getOwned())
                        .price(BackgroundType.fromName(result.getName()).getPrice())
                        .build())
                .collect(Collectors.toList());

        return BackgroundResponse.from(backgroundInfos);
    }
}

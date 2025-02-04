package com.ssafy.solvedpick.backgrounds.service;

import com.ssafy.solvedpick.backgrounds.domain.Background;
import com.ssafy.solvedpick.backgrounds.dto.BackgroundInfo;
import com.ssafy.solvedpick.backgrounds.dto.BackgroundQueryResult;
import com.ssafy.solvedpick.backgrounds.dto.BackgroundResponse;
import com.ssafy.solvedpick.backgrounds.repository.BackgroundRepository;
import com.ssafy.solvedpick.common.enums.BackgroundType;
import com.ssafy.solvedpick.ownedbackgrounds.domain.OwnedBackground;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.ownedbackgrounds.repository.OwnedBackgroundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BackgroundService {

    private final BackgroundRepository backgroundRepository;
    private final OwnedBackgroundRepository ownedBackgroundRepository;

    @Transactional(readOnly = true)
    public BackgroundResponse getAllBackgroundsWithOwnership(Long memberId) {
        List<BackgroundQueryResult> queryResults = backgroundRepository.findAllWithOwnership(memberId);

        List<BackgroundInfo> backgroundInfos = queryResults.stream()
                .map(result -> BackgroundInfo.builder()
                        .id(result.getId())
                        .name(result.getName())
                        .owned(result.getOwned())
                        .price(BackgroundType.fromName(result.getName()).getPrice())
                        .build())
                .collect(Collectors.toList());

        return BackgroundResponse.from(backgroundInfos);
    }

    @Transactional
    public void purchaseBackground(Long backgroundId, Member member) {
        Background background = backgroundRepository.findById(backgroundId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "존재하지 않는 배경입니다."));

        if (ownedBackgroundRepository.existsByMemberAndBackground(member, background)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "이미 소유중인 배경입니다.");
        }

        if (member.getPoint() < BackgroundType.fromName(background.getName()).getPrice()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "포인트가 부족합니다.");
        }

        member.usePoint(BackgroundType.fromName(background.getName()).getPrice());

        OwnedBackground ownedBackground = OwnedBackground.builder()
                .member(member)
                .background(background)
                .visible(false)
                .build();

        ownedBackgroundRepository.save(ownedBackground);
    }
}

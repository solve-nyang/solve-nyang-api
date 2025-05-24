package com.solvenyang.ownedbackgrounds.service;

import com.solvenyang.members.domain.Member;
import com.solvenyang.ownedbackgrounds.domain.OwnedBackground;
import com.solvenyang.ownedbackgrounds.dto.OwnedBackgroundDTO;
import com.solvenyang.ownedbackgrounds.dto.OwnedBackgroundResponse;
import com.solvenyang.ownedbackgrounds.repository.OwnedBackgroundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnedBackgroundService {

    private final OwnedBackgroundRepository ownedBackgroundRepository;

    @Transactional(readOnly = true)
    public OwnedBackgroundResponse getOwnedBackgrounds(Member member) {

        List<OwnedBackground> backgrounds = ownedBackgroundRepository.findAllByMember(member);

        List<OwnedBackgroundDTO> backgroundDTOS = backgrounds.stream()
                .map(background -> OwnedBackgroundDTO.builder()
                        .id(background.getId())
                        .name(background.getBackground().getName())
                        .visible(background.isVisible())
                        .build())
                .toList();

        return OwnedBackgroundResponse.builder()
                .backgrounds(backgroundDTOS)
                .build();
    }

    @Transactional
    public void updateBackgroundVisibility(Member member, Long backgroundId) {

        ownedBackgroundRepository.findByMemberAndVisibleTrue(member)
                .ifPresent(OwnedBackground::updateVisibility);

        OwnedBackground newBackground = ownedBackgroundRepository.findByIdAndMember(backgroundId, member)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "소유하지 않은 배경입니다."));

        newBackground.updateVisibility();
    }
}

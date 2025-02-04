package com.ssafy.solvedpick.ownedbackgrounds.service;

import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.ownedbackgrounds.domain.OwnedBackground;
import com.ssafy.solvedpick.ownedbackgrounds.dto.OwnedBackgroundDTO;
import com.ssafy.solvedpick.ownedbackgrounds.dto.OwnedBackgroundResponse;
import com.ssafy.solvedpick.ownedbackgrounds.repository.OwnedBackgroundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                        .build())
                .toList();

        return OwnedBackgroundResponse.builder()
                .backgrounds(backgroundDTOS)
                .build();
    }
}

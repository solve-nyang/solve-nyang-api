package com.ssafy.solvedpick.ownedbackgrounds.facade;

import com.ssafy.solvedpick.backgrounds.domain.Background;
import com.ssafy.solvedpick.backgrounds.repository.BackgroundRepository;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.ownedbackgrounds.domain.OwnedBackground;
import com.ssafy.solvedpick.ownedbackgrounds.repository.OwnedBackgroundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnedBackgroundFacade {

    private final OwnedBackgroundRepository ownedBackgroundRepository;
    private final BackgroundRepository backgroundRepository;

    public void addDefaultBackground(Member member) {
        Background defaultBackground = backgroundRepository.findByName("SpaceField");

        OwnedBackground ownedBackground = OwnedBackground.builder()
                .member(member)
                .background(defaultBackground)
                .visible(false)
                .build();

        ownedBackgroundRepository.save(ownedBackground);
    }
}

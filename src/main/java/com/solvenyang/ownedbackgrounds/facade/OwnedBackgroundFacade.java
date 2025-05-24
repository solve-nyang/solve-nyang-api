package com.solvenyang.ownedbackgrounds.facade;

import com.solvenyang.backgrounds.domain.Background;
import com.solvenyang.backgrounds.repository.BackgroundRepository;
import com.solvenyang.members.domain.Member;
import com.solvenyang.ownedbackgrounds.domain.OwnedBackground;
import com.solvenyang.ownedbackgrounds.repository.OwnedBackgroundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnedBackgroundFacade {

    private final OwnedBackgroundRepository ownedBackgroundRepository;
    private final BackgroundRepository backgroundRepository;

    public void addDefaultBackground(Member member) {
        Background defaultBackground = backgroundRepository.findByName("Space");

        OwnedBackground ownedBackground = OwnedBackground.builder()
                .member(member)
                .background(defaultBackground)
                .visible(false)
                .build();

        ownedBackgroundRepository.save(ownedBackground);
    }
}

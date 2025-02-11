package com.ssafy.solvedpick.memberPromotion.facade;

import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.avatars.domain.Avatar;
import com.ssafy.solvedpick.avatars.service.AvatarService;
import com.ssafy.solvedpick.memberPromotion.domain.Promotion;
import com.ssafy.solvedpick.memberPromotion.dto.CoinResponseDTO;
import com.ssafy.solvedpick.memberPromotion.dto.PromotionDrawResponseDTO;
import com.ssafy.solvedpick.memberPromotion.service.PromotionService;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.ownedavatar.service.OwnedAvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class PromotionFacade {

    private static final String[] PROMOTION_AVATARS = {"LikeCat", "CupidCat", "ChocoFondueCat"};

    private final PromotionService promotionService;
    private final AuthService authService;
    private final AvatarService avatarService;
    private final OwnedAvatarService ownedAvatarService;
    private final Random random = new Random();

    public CoinResponseDTO getMemberCoin() {
        Member member = authService.getCurrentMember();
        Promotion promotion = promotionService.getPromotionByMember(member);

        return CoinResponseDTO.builder()
                .coin(promotion.getCoin())
                .build();
    }

    public PromotionDrawResponseDTO drawPromotionAvatar() {
        Member member = authService.getCurrentMember();
        Promotion promotion = promotionService.getPromotionByMember(member);
        promotion.useCoin();

        int index = random.nextInt(PROMOTION_AVATARS.length);
        String selectedAvatarName = PROMOTION_AVATARS[index];
        Avatar selectedAvatar = avatarService.findByName(selectedAvatarName);
        ownedAvatarService.saveAvatar(member, selectedAvatar);

        return PromotionDrawResponseDTO.builder()
                .avatarName(selectedAvatarName)
                .build();
    }
}

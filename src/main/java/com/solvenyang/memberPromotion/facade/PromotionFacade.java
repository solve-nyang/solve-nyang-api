package com.solvenyang.memberPromotion.facade;

import com.solvenyang.auth.service.AuthService;
import com.solvenyang.avatars.domain.Avatar;
import com.solvenyang.avatars.service.AvatarService;
import com.solvenyang.memberPromotion.domain.Promotion;
import com.solvenyang.memberPromotion.dto.CoinResponseDTO;
import com.solvenyang.memberPromotion.dto.PromotionDrawResponseDTO;
import com.solvenyang.memberPromotion.service.PromotionService;
import com.solvenyang.members.domain.Member;
import com.solvenyang.ownedavatar.service.OwnedAvatarService;
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

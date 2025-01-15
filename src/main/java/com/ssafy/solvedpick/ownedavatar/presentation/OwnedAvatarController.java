package com.ssafy.solvedpick.ownedavatar.presentation;

import com.ssafy.solvedpick.ownedavatar.dto.MemberAvatarResponseDTO;
import com.ssafy.solvedpick.ownedavatar.dto.OwnedAvatarDTO;
import com.ssafy.solvedpick.ownedavatar.service.OwnedAvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my/avatar")
public class OwnedAvatarController {

    private final OwnedAvatarService ownedAvatarService;

    // TODO: JWT 기능의 부재로 현재는 Authorization header
    @GetMapping()
    public ResponseEntity<?> getMemberAvatar(@RequestHeader(value = "Authorization") Long memberId) {
        List<OwnedAvatarDTO> avatars = ownedAvatarService.getOwnedAvatars(memberId);
        MemberAvatarResponseDTO result = MemberAvatarResponseDTO.builder()
                .avatars(avatars)
                .build();

        return ResponseEntity.ok().body(result);
    }
}

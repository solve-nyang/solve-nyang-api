package com.ssafy.solvedpick.ownedavatar.presentation;

import com.ssafy.solvedpick.auth.service.AuthService;
import com.ssafy.solvedpick.ownedavatar.dto.*;
import com.ssafy.solvedpick.ownedavatar.service.OwnedAvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/me")
public class OwnedAvatarController {

    private final OwnedAvatarService ownedAvatarService;
    private final AuthService authService;

    @GetMapping("/avatar")
    public ResponseEntity<?> getMemberAvatar() {
        Long memberId = authService.getCurrentMember().getId();
        List<OwnedAvatarDTO> avatars = ownedAvatarService.getOwnedAvatars(memberId);
        OwnedAvatarResponseDTO result = OwnedAvatarResponseDTO.builder()
                .avatars(avatars)
                .build();

        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/avatar/{ownedAvatarId}")
    public ResponseEntity<?> updateAvatarVisibility(
            @PathVariable Long ownedAvatarId
    ) {
        ownedAvatarService.updateAvatarVisibility(ownedAvatarId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/avatar/reset")
    public ResponseEntity<?> setAllVisibilityFalse() {
        ownedAvatarService.setAllVisibilityFalse();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/extension")
    public ResponseEntity<?> getExtension(@RequestParam("username") String username) {
        ExtensionAvatarResponseDTO result = ownedAvatarService.getExtensionAvatars(username);

        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/sale")
    public ResponseEntity<AvatarSaleResponseDTO> sellAvatars(@RequestBody AvatarSaleRequestDTO request) {
        AvatarSaleResponseDTO result = ownedAvatarService.sellAvatars(request);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/collection")
    public ResponseEntity<?> getAvatarCollection() {
        AvatarCollectionResponseDTO result = ownedAvatarService.getAvatarCollection();

        return ResponseEntity.ok().body(result);
    }
}

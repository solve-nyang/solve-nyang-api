package com.ssafy.solvedpick.ownedavatar.presentation;


import com.ssafy.solvedpick.ownedavatar.dto.AvatarSaleRequestDto;
import com.ssafy.solvedpick.ownedavatar.dto.AvatarSaleResponseDto;
import com.ssafy.solvedpick.ownedavatar.service.OwnedAvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gacha")
@RequiredArgsConstructor
public class AvatarSaleController {
private final OwnedAvatarService ownedAvatarService;

@PatchMapping("/sale")
    public ResponseEntity<AvatarSaleResponseDto> sellAvatars(@RequestBody AvatarSaleRequestDto request) {
    AvatarSaleResponseDto response = ownedAvatarService.sellAvatars(request);

    return ResponseEntity.ok(response);
}
}

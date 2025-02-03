package com.ssafy.solvedpick.gacha.presentation;

import com.ssafy.solvedpick.gacha.dto.DrawRequest;
import com.ssafy.solvedpick.gacha.dto.DrawResponse;
import com.ssafy.solvedpick.gacha.dto.HasEventAvatarResponseDTO;
import com.ssafy.solvedpick.gacha.service.GachaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gacha")
public class GachaController {

    private static final String avatarName = "NewYearLuckCat";

    private final GachaService gachaService;

    @PostMapping("/draw")
    public ResponseEntity<DrawResponse> drawAvatars(@RequestBody DrawRequest drawRequest){
        DrawResponse drawResponse = gachaService.drawAvatars(drawRequest.getCount());
        return ResponseEntity.ok(drawResponse);
    }

    @PostMapping("/event")
    public ResponseEntity<?> getEventAvatar() {
        boolean result = gachaService.getEventAvatar(avatarName);
        if (result) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping("/event")
    public ResponseEntity<?> checkHasEventAvatar() {
        Boolean hasEventAvatar = gachaService.hasEventAvatar(avatarName);
        HasEventAvatarResponseDTO result = HasEventAvatarResponseDTO.builder()
                .hasEventAvatar(hasEventAvatar)
                .build();

        return ResponseEntity.ok().body(result);
    }
}

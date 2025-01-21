package com.ssafy.solvedpick.avatars.presentation;


import com.ssafy.solvedpick.avatars.dto.AvatarResponse;
import com.ssafy.solvedpick.avatars.service.AvatarService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/avatar")
@RequiredArgsConstructor
public class AvatarController {
    private final AvatarService avatarService;

    @GetMapping
    public ResponseEntity<AvatarResponse> getAllAvatars() {
        AvatarResponse response = avatarService.findAllAvatars();
        return ResponseEntity.ok(response);
    }
}

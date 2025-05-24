package com.solvenyang.avatars.presentation;


import com.solvenyang.avatars.dto.AvatarResponse;
import com.solvenyang.avatars.service.AvatarService;
import lombok.RequiredArgsConstructor;
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

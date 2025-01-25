package com.ssafy.solvedpick.composition.presentation;

import com.ssafy.solvedpick.composition.service.CompositionService;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/compose")
public class CompositionController {
    private final CompositionService compositionService;
    private final MemberRepository memberRepository;

    @GetMapping(value = "/{username}", produces = "image/svg+xml")
    public ResponseEntity<?> generateCompositeImage(@PathVariable String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Member not found: "+username));

        String svgImage = compositionService.generateCompositeImage(member);
        log.debug("imageimage = {}", svgImage);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/svg+xml"))
                .body(svgImage);
    }
}
package com.solvenyang.gacha.presentation;

import com.solvenyang.gacha.dto.DrawRequest;
import com.solvenyang.gacha.dto.DrawResponse;
import com.solvenyang.gacha.service.GachaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gacha")
public class GachaController {

    private final GachaService gachaService;

    @PostMapping("/draw")
    public ResponseEntity<DrawResponse> drawAvatars(@RequestBody DrawRequest drawRequest){
        DrawResponse drawResponse = gachaService.drawAvatars(drawRequest.getCount());
        return ResponseEntity.ok(drawResponse);
    }
}

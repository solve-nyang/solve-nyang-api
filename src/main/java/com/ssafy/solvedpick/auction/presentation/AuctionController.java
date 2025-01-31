package com.ssafy.solvedpick.auction.presentation;

import com.ssafy.solvedpick.auction.facade.AuctionFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auction")
public class AuctionController {

    private final AuctionFacade auctionFacade;

    // TODO: Use Converter
    @GetMapping()
    public ResponseEntity<?> searchMerchandise(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sort", defaultValue = "0") int sort,
            @RequestParam(value = "rate", required = false) int rate,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        // TODO: Get a response dto from facade
        auctionFacade.searchMerchandise(keyword, sort, page);

        return ResponseEntity.ok().build();
    }
}

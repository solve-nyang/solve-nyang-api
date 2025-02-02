package com.ssafy.solvedpick.auction.presentation;

import com.ssafy.solvedpick.auction.dto.*;
import com.ssafy.solvedpick.auction.facade.AuctionFacade;
import com.ssafy.solvedpick.common.dto.ResponseMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(value = "rarity", required = false) String rarity,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        SearchMerchandiseResponseDTO result = auctionFacade.searchMerchandise(keyword, rarity, sort, page);

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/sale")
    public ResponseEntity<?> sellAvatar(@RequestBody SellAvatarRequestDTO requestDTO) {
        ResponseMessageDTO result = auctionFacade.sellAvatar(requestDTO);

        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/sale")
    public ResponseEntity<?> cancelSale(@RequestBody AuctionCancelRequestDTO requestDTO) {
        auctionFacade.cancelSale(requestDTO);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMemberSalesHistory() {
        SalesHistoryResponseDTO result = auctionFacade.getSalesHistory();

        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/buy")
    public ResponseEntity<?> buyAvatar(@RequestBody AuctionBuyRequestDTO requestDTO) {
        auctionFacade.buyAvatar(requestDTO);

        return ResponseEntity.noContent().build();
    }
}

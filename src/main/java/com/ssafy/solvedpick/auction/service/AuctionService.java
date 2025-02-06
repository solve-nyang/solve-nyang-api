package com.ssafy.solvedpick.auction.service;

import com.ssafy.solvedpick.auction.domain.Auction;
import com.ssafy.solvedpick.auction.repository.AuctionRepository;
import com.ssafy.solvedpick.members.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
@Transactional
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;

    @Transactional(readOnly = true)
    public Page<Auction> findMerchandiseWithKeyword(String keyword, Pageable pageable) {
        return auctionRepository.findAllByOwnedAvatar_Avatar_KoreanNameContainingAndCancelledFalse(
                keyword, pageable
        );
    }

    @Transactional(readOnly = true)
    public Page<Auction> findMerchandise(Pageable pageable) {
        return auctionRepository.findAllByCancelledFalse(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Auction> findMerchandiseWithKeywordAndGrade(String keyword, int grade, Pageable pageable) {
        return auctionRepository
                .findAllByOwnedAvatar_Avatar_KoreanNameContainingAndOwnedAvatar_Avatar_GradeAndCancelledFalse(
                        keyword, grade, pageable
                );
    }

    @Transactional(readOnly = true)
    public Page<Auction> findMerchandiseWithGrade(int grade, Pageable pageable) {
        return auctionRepository.findAllByOwnedAvatar_Avatar_GradeAndCancelledFalse(grade, pageable);
    }

    public void save(Auction auction) {
        auctionRepository.save(auction);
    }

    public Auction cancelAuction(Long auctionId, Member member) {
        Auction auction = auctionRepository.findByIdAndOwnedAvatar_MemberAndSoldFalseAndCancelledFalse(
                        auctionId, member
                )
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        auction.cancel();
        return auction;
    }

    public Auction buyAvatar(Long id) {
        Auction auction = auctionRepository.findByIdAndSoldFalseAndCancelledFalse(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        auction.sold();

        return auction;
    }

    @Transactional(readOnly = true)
    public Page<Auction> findMemberHistoryAll(Member member, Pageable pageable) {
        return auctionRepository.findAllByOwnedAvatar_Member(member, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Auction> findMemberHistorySold(Member member, Pageable pageable) {
        return auctionRepository.findAllByOwnedAvatar_MemberAndSoldTrue(member, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Auction> findMemberHistoryCancelled(Member member, Pageable pageable) {
        return auctionRepository.findAllByOwnedAvatar_MemberAndCancelledTrue(member, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Auction> findMemberHistoryOnStatus(Member member, Pageable pageable) {
        return auctionRepository.findAllByOwnedAvatar_MemberAndSoldFalseAndCancelledFalse(member, pageable);
    }
}

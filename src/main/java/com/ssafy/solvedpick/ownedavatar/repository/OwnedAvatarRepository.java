package com.ssafy.solvedpick.ownedavatar.repository;

import com.ssafy.solvedpick.ownedavatar.domain.OwnedAvatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OwnedAvatarRepository extends JpaRepository<OwnedAvatar, Long> {

    @Query("SELECT a " +
            "FROM OwnedAvatar a " +
            "LEFT JOIN FETCH a.member m " +
            "LEFT JOIN FETCH a.avatar " +
            "WHERE m.id = :memberId")
    List<OwnedAvatar> findAllByMemberId(@Param("memberId") Long memberId);
}

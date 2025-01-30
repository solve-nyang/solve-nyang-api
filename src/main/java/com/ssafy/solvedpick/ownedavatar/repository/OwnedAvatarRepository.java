package com.ssafy.solvedpick.ownedavatar.repository;

import com.ssafy.solvedpick.avatars.domain.Avatar;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.ownedavatar.domain.OwnedAvatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OwnedAvatarRepository extends JpaRepository<OwnedAvatar, Long> {

    Optional<OwnedAvatar> findById(@Param("ownedAvatarId") Long ownedAvatarId);

    @Query("SELECT oa " +
            "FROM OwnedAvatar oa " +
            "LEFT JOIN FETCH oa.avatar " +
            "WHERE oa.member.id = :memberId " +
            "ORDER BY oa.avatar.grade DESC")
    List<OwnedAvatar> findAllByMemberIdAndSoldFalse(@Param("memberId") Long memberId);

    List<OwnedAvatar> findAllByMemberAndVisibleTrueAndSoldFalse(Member member);

    List<OwnedAvatar> findAllByMemberAndVisibleExtensionTrueAndSoldFalse(Member member);

    List<OwnedAvatar> findAllByIdInAndMemberAndSoldFalse(List<Long> id, Member currentMember);

    Boolean existsByMemberAndAvatar(Member member, Avatar avatar);

    @Query("SELECT DISTINCT oa.avatar " +
            "FROM OwnedAvatar oa " +
            "WHERE oa.member = :member ")
    List<Avatar> findDistinctByMemberAndAvatar(@Param("member") Member member);
}

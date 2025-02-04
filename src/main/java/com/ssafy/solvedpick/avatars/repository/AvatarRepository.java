package com.ssafy.solvedpick.avatars.repository;

import com.ssafy.solvedpick.avatars.domain.Avatar;
import com.ssafy.solvedpick.members.domain.Member;
import com.ssafy.solvedpick.ownedavatar.dto.AvatarCollectionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    List<Avatar> findAllByGrade(int grade);

    List<Avatar> findAllByGradeBetween(int grade1, int grade2);

    Avatar findByName(String name);

    @Query("SELECT new com.ssafy.solvedpick.ownedavatar.dto.AvatarCollectionDTO(" +
            "a.name, a.grade, " +
            "COUNT(oa) > 0) " +
            "FROM Avatar a " +
            "LEFT JOIN OwnedAvatar oa ON a.id = oa.avatar.id " +
            "AND oa.member.id = :memberId " +
            "GROUP BY a.name, a.grade " +
            "ORDER BY a.grade DESC")
    List<AvatarCollectionDTO> getAvatarCollections(@Param("memberId") Long memberId);
}

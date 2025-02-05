package com.ssafy.solvedpick.backgrounds.repository;

import com.ssafy.solvedpick.backgrounds.domain.Background;
import com.ssafy.solvedpick.backgrounds.dto.BackgroundQueryResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BackgroundRepository extends JpaRepository<Background, Long> {

    Background findByName(String space);

    @Query("SELECT new com.ssafy.solvedpick.backgrounds.dto.BackgroundQueryResult(" +
            "b.id, "+
            "b.name, " +
            "COUNT(ob) > 0 )" +
            "FROM Background b " +
            "LEFT JOIN OwnedBackground ob ON b = ob.background AND ob.member.id = :memberId " +
            "GROUP BY b.name " +
            "ORDER BY b.id")
    List<BackgroundQueryResult> findAllWithOwnership(@Param("memberId") Long memberId);
}
package com.ssafy.solvedpick.ownedbackgrounds.repository;

import com.ssafy.solvedpick.ownedbackgrounds.domain.OwnedBackground;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnedBackgroundRepository extends JpaRepository<OwnedBackground, Long> {
}

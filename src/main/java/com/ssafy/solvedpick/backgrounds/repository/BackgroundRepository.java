package com.ssafy.solvedpick.backgrounds.repository;

import com.ssafy.solvedpick.backgrounds.domain.Background;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BackgroundRepository extends JpaRepository<Background, Long> {
}

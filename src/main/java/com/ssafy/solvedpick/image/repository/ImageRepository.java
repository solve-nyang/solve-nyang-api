package com.ssafy.solvedpick.image.repository;

import com.ssafy.solvedpick.image.domain.Image;
import com.ssafy.solvedpick.image.dto.ContestImageResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findImageByVisibleTrue();
}

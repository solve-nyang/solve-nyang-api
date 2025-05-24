package com.solvenyang.image.repository;

import com.solvenyang.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findImageByVisibleTrue();
}

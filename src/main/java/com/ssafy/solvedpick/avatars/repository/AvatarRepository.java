package com.ssafy.solvedpick.avatars.repository;

import com.ssafy.solvedpick.avatars.domain.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    List<Avatar> findAllByGrade(int grade);
}

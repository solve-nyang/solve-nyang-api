package com.ssafy.solvedpick.avatars.repository;

import com.ssafy.solvedpick.avatars.domain.OwnedAvatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnedAvatarRepository extends JpaRepository<OwnedAvatar, Long> {
}

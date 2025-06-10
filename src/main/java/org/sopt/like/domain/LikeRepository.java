package org.sopt.like.domain;

import org.sopt.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

  boolean existsByUserAndTargetTypeAndTargetId(User user, TargetType targetType, Long targetId);
}

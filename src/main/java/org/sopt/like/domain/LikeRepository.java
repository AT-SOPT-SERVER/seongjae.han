package org.sopt.like.domain;

import java.util.List;
import org.sopt.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

  boolean existsByUserAndTargetTypeAndTargetId(User user, TargetType targetType, Long targetId);
  List<Like> findByTargetTypeAndTargetId(TargetType targetType, Long targetId);
}

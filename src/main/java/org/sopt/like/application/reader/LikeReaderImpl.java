package org.sopt.like.application.reader;

import lombok.RequiredArgsConstructor;
import org.sopt.like.domain.LikeRepository;
import org.sopt.like.domain.TargetType;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeReaderImpl implements LikeReader {

  private final LikeRepository likeRepository;

  @Override
  public boolean existPostLike(final User user, final Long postId) {
    return likeRepository.existsByUserAndTargetTypeAndTargetId(user, TargetType.POST, postId);
  }
}

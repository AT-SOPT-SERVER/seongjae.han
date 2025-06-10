package org.sopt.like.application.reader;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.comment.domain.Comment;
import org.sopt.like.domain.Like;
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

  @Override
  public List<Like> getByPostId(final Long postId) {

    return likeRepository.findByTargetTypeAndTargetId(TargetType.POST, postId);
  }

  @Override
  public boolean existCommentLike(final User user, final Comment comment) {

    return likeRepository.existsByUserAndTargetTypeAndTargetId(user, TargetType.COMMENT, comment.getId());
  }
}

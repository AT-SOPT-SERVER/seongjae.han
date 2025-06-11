package org.sopt.like.application.reader;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.comment.domain.Comment;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.like.domain.Like;
import org.sopt.like.domain.LikeRepository;
import org.sopt.like.domain.TargetType;
import org.sopt.post.domain.Post;
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

  @Override
  public Like getByUserAndPostOrThrow(final User user, final Post post) {

    return likeRepository.findByUserAndTargetTypeAndTargetId(user, TargetType.POST, post.getId())
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_Like));
  }

  @Override
  public Like getByUserAndCommentOrThrow(final User user, final Comment comment) {

    return likeRepository.findByUserAndTargetTypeAndTargetId(user, TargetType.COMMENT, comment.getId())
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_Like));
  }

  @Override
  public void delete(final Like like) {
    likeRepository.delete(like);
  }
}

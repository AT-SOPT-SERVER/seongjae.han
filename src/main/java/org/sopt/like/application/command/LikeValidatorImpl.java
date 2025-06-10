package org.sopt.like.application.command;

import lombok.RequiredArgsConstructor;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.like.application.reader.LikeReader;
import org.sopt.post.application.reader.PostReader;
import org.sopt.post.domain.Post;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeValidatorImpl implements LikeValidator {

  private final LikeReader likeReader;
  private final PostReader postReader;

  @Override
  public void validatePostLikeDuplicate(final User user,
      final Long postId) {
    if (likeReader.existPostLike(user, postId)) {
      throw new ApiException(ErrorCode.POST_LIKE_ALREADY_EXIST);
    }
  }

  @Override
  public void validatePostExists(final Long postId) {
    final Post post = postReader.getPostOrThrow(postId);
  }

}

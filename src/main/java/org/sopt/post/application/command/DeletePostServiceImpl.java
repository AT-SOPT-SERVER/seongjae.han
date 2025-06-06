package org.sopt.post.application.command;

import lombok.RequiredArgsConstructor;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.post.application.reader.PostReader;
import org.sopt.post.application.writer.PostWriter;
import org.sopt.post.domain.Post;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeletePostServiceImpl implements DeletePostService {

  private final PostWriter postWriter;
  private final PostReader postReader;
  private final UserReader userReader;

  @Override
  public void execute(final Long userId, final Long postId) {
    final User user = userReader.getUserOrThrow(userId);
    final Post post = postReader.getPostOrThrow(postId);

    validateUserPostOwnership(user, post);

    postWriter.delete(post);
  }

  private static void validateUserPostOwnership(final User user, final Post post) {
    if (!user.equals(post.getUser())) {
      throw new ApiException(ErrorCode.USER_NOT_PERMITTED);
    }
  }
}

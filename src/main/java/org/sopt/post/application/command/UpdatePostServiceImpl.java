package org.sopt.post.application.command;

import lombok.RequiredArgsConstructor;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.post.application.reader.PostReader;
import org.sopt.post.domain.Post;
import org.sopt.post.application.dto.PostServiceRequestDto.UpdatePostServiceRequest;
import org.sopt.post.application.dto.PostServiceResponseDto.PostItemServiceResponse;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdatePostServiceImpl implements
    UpdatePostService {

  private final PostReader postReader;
  private final PostValidator postValidator;
  private final UserReader userReader;

  @Override
  public PostItemServiceResponse execute(final Long userId, final UpdatePostServiceRequest updateRequest) {
    final User user = userReader.getUserOrThrow(userId);
    Post post = postReader.getPostOrThrow(updateRequest.postId());

    validateUserPostOwnerShip(user, post);
    postValidator.validateDuplicateTitle(updateRequest.title());

    post.update(updateRequest.title(), updateRequest.content());

    return PostItemServiceResponse.from(post);

  }

  private static void validateUserPostOwnerShip(final User user, final Post post) {
    if (!user.equals(post.getUser())) {
      throw new ApiException(ErrorCode.USER_NOT_PERMITTED);
    }
  }
}

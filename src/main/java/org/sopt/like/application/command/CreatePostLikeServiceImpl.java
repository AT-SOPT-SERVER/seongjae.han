package org.sopt.like.application.command;

import lombok.RequiredArgsConstructor;
import org.sopt.like.application.dto.LikeServiceRequestDto.CreatePostLikeServiceRequest;
import org.sopt.like.application.dto.LikeServiceResponseDto.CreatePostLikeServiceResponse;
import org.sopt.like.application.writer.LikeWriter;
import org.sopt.like.domain.Like;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreatePostLikeServiceImpl implements
    CreatePostLikeService {

  private final LikeWriter likeWriter;
  private final UserReader userReader;
  private final LikeValidator likeValidator;

  @Override
  public CreatePostLikeServiceResponse execute(final Long userId,
      final CreatePostLikeServiceRequest serviceRequest) {
    final User user = userReader.getUserOrThrow(userId);

    likeValidator.validatePostExists(serviceRequest.postId());
    likeValidator.validatePostLikeDuplicate(user, serviceRequest.postId());

    final Like like = Like.ofPost(serviceRequest.postId(), user);
    Like saved = likeWriter.save(like);

    return CreatePostLikeServiceResponse.from(saved);
  }
}

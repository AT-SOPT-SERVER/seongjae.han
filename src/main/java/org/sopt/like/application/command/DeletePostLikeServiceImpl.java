package org.sopt.like.application.command;

import lombok.RequiredArgsConstructor;
import org.sopt.like.application.dto.LikeServiceRequestDto.DeletePostLikeServiceRequest;
import org.sopt.like.application.dto.LikeServiceResponseDto.DeletePostLikeServiceResponse;
import org.sopt.like.application.reader.LikeReader;
import org.sopt.like.domain.Like;
import org.sopt.post.application.reader.PostReader;
import org.sopt.post.domain.Post;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeletePostLikeServiceImpl implements
    DeletePostLikeService {

  private final UserReader userReader;
  private final PostReader postReader;
  private final LikeReader likeReader;

  @Override
  public DeletePostLikeServiceResponse execute(final Long userId,
      final DeletePostLikeServiceRequest serviceRequest) {

    final User user = userReader.getUserOrThrow(userId);
    final Post post = postReader.getPostOrThrow(serviceRequest.postId());

    Like like = likeReader.getByUserAndPostOrThrow(user, post);
    likeReader.delete(like);

    return DeletePostLikeServiceResponse.from(like);
  }
}

package org.sopt.like.application.command;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.application.reader.CommentReader;
import org.sopt.comment.domain.Comment;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.like.application.dto.LikeServiceRequestDto.CreateCommentLikeServiceRequest;
import org.sopt.like.application.dto.LikeServiceResponseDto.CreateCommentLikeServiceResponse;
import org.sopt.like.application.reader.LikeReader;
import org.sopt.like.application.writer.LikeWriter;
import org.sopt.like.domain.Like;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateCommentLikeServiceImpl implements CreateCommentLikeService {

  private final CommentReader commentReader;
  private final UserReader userReader;
  private final LikeReader likeReader;
  private final LikeWriter likeWriter;

  @Override
  public CreateCommentLikeServiceResponse execute(
      final Long userId,
      final CreateCommentLikeServiceRequest serviceRequest
  ) {
    final User user = userReader.getUserOrThrow(userId);
    final Comment comment = commentReader.getCommentOrThrow(serviceRequest.commentId());

    validateCommentLikeDuplicate(user, comment);

    final Like like = Like.ofComment(serviceRequest.commentId(), user);
    Like saved = likeWriter.save(like);

    return CreateCommentLikeServiceResponse.from(saved);
  }

  private void validateCommentLikeDuplicate(final User user, final Comment comment) {
    if (likeReader.existCommentLike(user, comment)) {
      throw new ApiException(ErrorCode.COMMENT_LIKE_ALREADY_EXIST);
    }
  }
}

package org.sopt.like.application.command;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.application.reader.CommentReader;
import org.sopt.comment.domain.Comment;
import org.sopt.like.application.dto.LikeServiceRequestDto.DeleteCommentLikeServiceRequest;
import org.sopt.like.application.dto.LikeServiceResponseDto.DeleteCommentLikeServiceResponse;
import org.sopt.like.application.reader.LikeReader;
import org.sopt.like.domain.Like;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCommentLikeServiceImpl implements
    DeleteCommentLikeService {

  private final UserReader userReader;
  private final CommentReader commentReader;
  private final LikeReader likeReader;

  @Override
  public DeleteCommentLikeServiceResponse execute(final Long userId,
      final DeleteCommentLikeServiceRequest serviceRequest) {
    final User user = userReader.getUserOrThrow(userId);
    final Comment comment = commentReader.getCommentOrThrow(serviceRequest.commentId());

    Like like = likeReader.getByUserAndCommentOrThrow(user, comment);
    likeReader.delete(like);

    return DeleteCommentLikeServiceResponse.from(like);
  }
}

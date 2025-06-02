package org.sopt.comment.application.command;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.application.reader.CommentReader;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.dto.CommentRequestDto.CommentUpdateRequestDto;
import org.sopt.comment.dto.CommentResponseDto.CommentItemDto;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateCommentServiceImpl implements UpdateCommentService {

  private final CommentReader commentReader;
  private final UserReader userReader;

  @Override
  @Transactional
  public CommentItemDto execute(final CommentUpdateRequestDto commentUpdateRequestDto) {

    final User user = userReader.getUserOrThrow(commentUpdateRequestDto.userId());
    final Comment comment = commentReader.getCommentOrThrow(commentUpdateRequestDto.commentId());

    validateCommentOwnership(comment, user);

    comment.update(commentUpdateRequestDto.content());

    return CommentItemDto.from(comment);
  }

  private static void validateCommentOwnership(final Comment comment, final User user) {
    if (!comment.getUser().equals(user)) {
      throw new ApiException(ErrorCode.USER_NOT_PERMITTED);
    }
  }
}

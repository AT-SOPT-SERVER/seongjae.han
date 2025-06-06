package org.sopt.comment.application.command;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.application.reader.CommentReader;
import org.sopt.comment.application.writer.CommentWriter;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.application.dto.CommentServiceRequestDto.CommentDeleteServiceRequestDto;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCommentServiceImpl implements
    DeleteCommentService {

  private final UserReader userReader;
  private final CommentReader commentReader;
  private final CommentWriter commentWriter;

  @Override
  public void execute(final CommentDeleteServiceRequestDto commentDeleteRequestDto) {
    final Comment comment = commentReader.getCommentOrThrow(commentDeleteRequestDto.commentId());
    final User user = userReader.getUserOrThrow(commentDeleteRequestDto.userId());

    validateCommentOwnerShip(comment, user);

    commentWriter.delete(comment);
  }

  private static void validateCommentOwnerShip(final Comment comment, final User user) {
    if (!comment.getUser().equals(user)) {
      throw new ApiException(ErrorCode.USER_NOT_PERMITTED);
    }
  }
}

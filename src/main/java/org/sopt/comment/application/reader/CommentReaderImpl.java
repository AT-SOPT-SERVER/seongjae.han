package org.sopt.comment.application.reader;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.domain.CommentRepository;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentReaderImpl implements CommentReader {

  private final CommentRepository commentRepository;

  @Override
  public Comment getCommentOrThrow(final Long commentId) {
    return  commentRepository.findById(commentId)
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_COMMENT));
  }
}

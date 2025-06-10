package org.sopt.comment.application.reader;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.domain.CommentRepository;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentReaderImpl implements CommentReader {

  private final CommentRepository commentRepository;

  @Override
  public Comment getCommentOrThrow(final Long commentId) {
    return commentRepository.findById(commentId)
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_COMMENT));
  }

  @Override
  public Page<Comment> getCommentsByPostId(final Long postId, final Pageable pageRequest) {
    return commentRepository.findByPostId(postId, pageRequest);
  }
}

package org.sopt.comment.application.writer;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.domain.CommentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentWriterImpl implements CommentWriter {

  private final CommentRepository commentRepository;

  @Override
  public Comment save(final Comment comment) {
    return commentRepository.save(comment);
  }

  @Override
  public void delete(final Comment comment) {
    commentRepository.delete(comment);
  }
}

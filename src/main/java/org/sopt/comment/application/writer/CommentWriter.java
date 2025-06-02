package org.sopt.comment.application.writer;

import org.sopt.comment.domain.Comment;

public interface CommentWriter {

  Comment save(Comment comment);

  void delete(Comment comment);
}

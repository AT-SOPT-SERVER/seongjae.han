package org.sopt.comment.application.reader;


import org.sopt.comment.domain.Comment;

public interface CommentReader {

  Comment getCommentOrThrow(final Long commentId);
}

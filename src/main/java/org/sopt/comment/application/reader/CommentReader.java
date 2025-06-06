package org.sopt.comment.application.reader;


import org.sopt.comment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentReader {

  Comment getCommentOrThrow(final Long commentId);

  Page<Comment> getCommentsByPostId(Long aLong, Pageable pageRequest);
}

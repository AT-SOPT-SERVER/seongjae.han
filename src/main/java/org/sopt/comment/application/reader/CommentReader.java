package org.sopt.comment.application.reader;


import java.util.List;
import org.sopt.comment.domain.Comment;
import org.sopt.like.domain.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentReader {

  Comment getCommentOrThrow(final Long commentId);

  Page<Comment> getCommentsByPostId(Long postId, Pageable pageRequest);
}

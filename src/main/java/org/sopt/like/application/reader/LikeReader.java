package org.sopt.like.application.reader;

import java.util.List;
import org.sopt.comment.domain.Comment;
import org.sopt.like.domain.Like;
import org.sopt.post.domain.Post;
import org.sopt.user.domain.User;

public interface LikeReader {

  boolean existPostLike(User user, Long postId);

  List<Like> getByPostId(Long postId);

  boolean existCommentLike(User user, Comment comment);

  Like getByUserAndPostOrThrow(User user, Post post);

  Like getByUserAndCommentOrThrow(User user, Comment comment);

  void delete(Like like);
}

package org.sopt.support.fixture;

import org.sopt.comment.domain.Comment;
import org.sopt.post.domain.Post;
import org.sopt.user.domain.User;

public class CommentFixture {

  public static Comment create(String content, User user, Post post) {
    return Comment.of(content, user, post);
  }

}

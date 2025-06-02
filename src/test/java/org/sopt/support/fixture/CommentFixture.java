package org.sopt.support.fixture;

import org.sopt.comment.domain.Comment;
import org.sopt.post.domain.Post;
import org.sopt.user.domain.User;
import org.springframework.test.util.ReflectionTestUtils;

public class CommentFixture {

  public static Comment create(Long id, String content, User user, Post post) {
    final Comment comment = Comment.of(content, user, post);
    ReflectionTestUtils.setField(comment, "id", id);

    return comment;
  }

  public static Comment create(String content, User user, Post post) {
    return Comment.of(content, user, post);
  }

}

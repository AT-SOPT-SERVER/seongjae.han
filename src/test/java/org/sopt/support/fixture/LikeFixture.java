package org.sopt.support.fixture;

import org.sopt.like.domain.Like;
import org.sopt.post.domain.Post;
import org.sopt.user.domain.User;
import org.springframework.test.util.ReflectionTestUtils;

public class LikeFixture {

  public static Like createPostLike(Long likeId, Post post, User user) {
    Like like = Like.ofPost(post.getId(), user);
    ReflectionTestUtils.setField(like, "id", likeId);

    return like;
  }
}

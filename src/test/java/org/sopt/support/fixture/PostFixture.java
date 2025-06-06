package org.sopt.support.fixture;

import org.sopt.post.domain.Post;
import org.sopt.user.domain.User;
import org.springframework.test.util.ReflectionTestUtils;

public class PostFixture {

  public static Post create(Long id, User user) {
    Post post = Post.of("테스트 제목", "테스트 내용", user);
    ReflectionTestUtils.setField(post, "id", id);

    return post;
  }
}

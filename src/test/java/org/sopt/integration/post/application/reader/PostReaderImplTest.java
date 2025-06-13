package org.sopt.integration.post.application.reader;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sopt.post.application.reader.PostReader;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.PostRepository;
import org.sopt.user.domain.User;
import org.sopt.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("postReader 통합 테스트")
class PostReaderImplTest {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PostReader postReader;

  @BeforeEach
  void setUp() {
    final User user = User.of("name", "email@.com");
    userRepository.save(user);
    postRepository.saveAll(List.of(
        Post.of("test1", "content", user),
        Post.of("test2", "content2", user)
    ));
  }

  @Test
  @DisplayName("queryDsl을 통해 전체 게시글이 조회된다.")
  void 전체_게시글을_조회한다() {
    // when
    List<Post> posts = postReader.getPosts();

    // then
    assertThat(posts).hasSize(2);
    assertThat(posts).extracting("title")
        .containsExactlyInAnyOrder("test1", "test2");
  }
}

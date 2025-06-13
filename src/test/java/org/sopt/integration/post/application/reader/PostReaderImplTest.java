package org.sopt.integration.post.application.reader;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.IntStream;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    IntStream.rangeClosed(1, 20).forEach(i -> {
      postRepository.save(Post.of("title" + i, "content" + i, user));
    });

  }

  @Test
  @DisplayName("PostReaderImpl: queryDsl을 통해 전체 게시글이 조회된다.")
  void 전체_게시글을_조회한다() {
    // when
    List<Post> posts = postReader.getPosts();

    // then
    assertThat(posts).hasSize(20);
    assertThat(posts).extracting("title")
        .containsExactlyInAnyOrderElementsOf(IntStream.rangeClosed(1, 20).mapToObj(i ->"title" + i).toList());
  }

  @Test
  @DisplayName("PostReaderImpl: 게시글 목록을 페이징하여 조회한다")
  void getPosts_withPagination_success() {
    // given
    Pageable pageable = PageRequest.of(0, 10); // 첫 페이지, 10개

    // when
    Page<Post> posts = postReader.getPosts(pageable);

    // then
    assertThat(posts.getTotalElements()).isEqualTo(20);
    assertThat(posts.getContent()).hasSize(10); // 10개만 조회됨
    assertThat(posts.getNumber()).isEqualTo(0); // page 0
    assertThat(posts.getTotalPages()).isEqualTo(2);
  }
}

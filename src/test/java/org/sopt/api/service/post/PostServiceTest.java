package org.sopt.api.service.post;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sopt.api.service.post.dto.PostRequestDto.CreateRequest;
import org.sopt.api.service.post.dto.PostResponseDto;
import org.sopt.domain.post.Post;
import org.sopt.domain.post.PostRepository;
import org.sopt.domain.user.User;
import org.sopt.domain.user.UserRepository;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.util.TimeIntervalUtil;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
class PostServiceTest {

  @InjectMocks
  PostService postService;

  @Mock
  private PostRepository postRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private TimeIntervalUtil postTimeIntervalUtil;

  @Nested
  @DisplayName("게시글 생성 테스트")
  class createPostTest {

    @Test
    @DisplayName("게시글 생성을 성공한다.")
    void createPost_success() {
      // given
      Long userId = 1L;
      CreateRequest createRequest = new CreateRequest("test", "content");
      User user = User.of("name", "email");
      Post newPost = Post.of(createRequest.title(), createRequest.content(), user);

      given(userRepository.findById(userId)).willReturn(Optional.of(user));
      given(postTimeIntervalUtil.isAvailable()).willReturn(true);
      given(postRepository.existsByTitle(any(String.class))).willReturn(false);
      given(postRepository.save(any(Post.class))).willReturn(newPost);

      // when
      PostResponseDto.itemDto result = postService.createPost(userId, createRequest);

      // then
      assertThat(result).isNotNull();
      assertThat(result.title()).isEqualTo("test");
      assertThat(result.content()).isEqualTo("content");
      assertThat(result.writerName()).isEqualTo("name");
    }

    @DisplayName("유저가 없는 경우 생성되지 않는다.")
    @Test
    void whenUserNotExist_ThenCreatePost_ShouldFail() {
      // given
      Long userId = 1L;
      CreateRequest createRequest = new CreateRequest("test", "email");
      User user = User.of("name", "email");
      Post newPost = Post.of(createRequest.title(), createRequest.content(), user);

      given(userRepository.findById(userId)).willReturn(Optional.empty());
      given(postTimeIntervalUtil.isAvailable()).willReturn(true);
      given(postRepository.existsByTitle(any(String.class))).willReturn(false);
      given(postRepository.save(any(Post.class))).willReturn(newPost);

      // when & then
      assertThatThrownBy(() -> postService.createPost(userId, createRequest))
          .isInstanceOf(ApiException.class)
          .hasMessage("유저 정보가 존재하지 않습니다.");
    }

    @DisplayName("게시글 작성 쿨타임이 다되지 않은 경우 작성되지 않는다")
    @Test
    void whenPostCreateCoolDownNotComplete_ThenCreatePost_ShouldFail() {
      // given
      Long userId = 1L;
      CreateRequest createRequest = new CreateRequest("test", "email");
      User user = User.of("name", "email");
      Post newPost = Post.of(createRequest.title(), createRequest.content(), user);

      given(userRepository.findById(userId)).willReturn(Optional.of(user));
      given(postTimeIntervalUtil.isAvailable()).willReturn(false);
      given(postRepository.existsByTitle(any(String.class))).willReturn(false);
      given(postRepository.save(any(Post.class))).willReturn(newPost);

      // when & then
      assertThatThrownBy(() -> postService.createPost(userId, createRequest))
          .isInstanceOf(ApiException.class)
          .hasMessage("게시글은 3분 간격으로만 작성할 수 있습니다.");
    }

    @DisplayName("게시글이 제목이 중복인 경우 생성되지 않는다.")
    @Test
    void whenPostTitleDuplicate_ThenCreatePost_ShouldFail() {
      // given
      Long userId = 1L;
      CreateRequest createRequest = new CreateRequest("test", "email");
      User user = User.of("name", "email");
      Post newPost = Post.of(createRequest.title(), createRequest.content(), user);

      given(userRepository.findById(userId)).willReturn(Optional.of(user));
      given(postTimeIntervalUtil.isAvailable()).willReturn(true);
      given(postRepository.existsByTitle(any(String.class))).willReturn(true);
      given(postRepository.save(any(Post.class))).willReturn(newPost);

      // when & then
      assertThatThrownBy(() -> postService.createPost(userId, createRequest))
          .isInstanceOf(ApiException.class)
          .hasMessage("이미 존재하는 게시글 제목입니다.");
    }
  }


  @Test
  void getAllPosts() {
  }

  @Test
  void getPostById() {
  }

  @Test
  void deletePostById() {
  }

  @Test
  void updatePostTitle() {
  }

  @Test
  void searchPostsByKeyword() {
  }
}
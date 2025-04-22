package org.sopt.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sopt.domain.Post;
import org.sopt.repository.PostRepository;
import org.sopt.util.TimeIntervalUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

  @InjectMocks
  PostService postService;

  @Mock
  PostRepository postRepository;

  @Mock
  TimeIntervalUtil timeIntervalUtil;

  @Nested
  @DisplayName("Post 생성 테스트")
  class PostCreateTest {

    @DisplayName("성공 - Post 생성하기")
    @Test
    void createPost_ShouldSuccess() {
      // given
      String dummyTitle = "dummyTitle";
      Post mockPost = mock(Post.class);
      given(mockPost.getTitle()).willReturn(dummyTitle);

      given(postRepository.existsByTitle(dummyTitle)).willReturn(false);
      given(timeIntervalUtil.isAvailable()).willReturn(true);

      given(postRepository.save(any(Post.class))).willReturn(mockPost);

      // when
      Post createdPost = postService.createPost(dummyTitle);

      // then
      assertThat(createdPost.getTitle()).isEqualTo(dummyTitle);

      verify(postRepository, times(1)).existsByTitle(dummyTitle);
      verify(postRepository, times(1)).save(any(Post.class));
      verify(timeIntervalUtil, times(1)).startTimer();
    }

    @DisplayName("실패 - Post 중복된 제목")
    @Test
    void createPost_duplicateTitle() {
      // given
      String dummyTitle = "dummyTitle";

      given(postRepository.existsByTitle(dummyTitle)).willReturn(true);
      given(timeIntervalUtil.isAvailable()).willReturn(true);

      // when & then
      assertThatThrownBy(() -> postService.createPost(dummyTitle))
          .isInstanceOf(RuntimeException.class)
          .hasMessageContaining("중복된 제목의 게시물입니다.");

      verify(postRepository, times(0)).save(any(Post.class));
      verify(timeIntervalUtil, times(0)).startTimer();
    }

    @DisplayName("실패 - Post 3분 내 재시도")
    @Test
    void createPost_TimeInterval() {
      // given
      String dummyTitle = "dummyTitle";

      given(timeIntervalUtil.isAvailable()).willReturn(false);

      // when & then
      assertThatThrownBy(() -> postService.createPost(dummyTitle))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("아직 새로운 게시물을 작성하실 수 없습니다.");

      verify(postRepository, times(0)).save(any(Post.class));
      verify(timeIntervalUtil, times(0)).startTimer();
    }
  }
}

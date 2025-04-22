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
  class PostCreateTest {

    @DisplayName("Post 생성하기 성공")
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
  }
}

package org.sopt.service;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sopt.domain.Post;
import org.sopt.exceptions.PostNotFoundException;
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

  @DisplayName("Post 전체 리스트 테스트")
  @Nested
  class PostListTest {

    @Test
    @DisplayName("성공 - 전체 리스트")
    void getAllPosts_ShouldSuccess() {
      // given
      List<Post> mockPosts = List.of(
          new Post("제목1"),
          new Post("제목2")
      );
      given(postRepository.findAll()).willReturn(mockPosts);

      // when
      List<Post> result = postService.getAllPosts();

      // then
      assertThat(result).hasSize(2);
      assertThat(result).extracting("title")
          .containsExactly("제목1", "제목2");
    }
  }

  @DisplayName("Post 게시물 아이디 검색 테스트")
  @Nested
  class PostGetPostByIdTest {

    @Test
    @DisplayName("성공 - 아이디로 검색")
    void getPostById_ShouldSuccess() {
      // given
      Post mockPost = new Post("제목");
      Long dummyId = 1L;
      given(postRepository.findFirstById(dummyId)).willReturn(mockPost);

      // when
      Post result = postService.getPostById(1L);

      // then
      assertThat(result).isNotNull();
      assertThat(result.getTitle()).isEqualTo("제목");
    }

    @Test
    @DisplayName("실패 - 존재하지 않는 아이디")
    void getPostById_NotExist() {
      // given
      Long dummyId = 1L;
      given(postRepository.findFirstById(dummyId)).willReturn(null);

      // when
      Post result = postService.getPostById(1L);

      // then
      assertThat(result).isNull();
    }
  }

  @DisplayName("Post 게시물 삭제 테스트")
  @Nested
  class PostDeleteTest {
    @DisplayName("성공 - Id로 삭제")
    @Test
    void deletePostById_deletesPost() {
      // given
      Long deleteId = 1L;

      // when
      postService.deletePostById(deleteId);

      // then
      verify(postRepository, times(1)).deleteById(deleteId);
    }
  }

  @DisplayName("Post 게시물 수정 테스트")
  @Nested
  class PostUpdateTest {
    @DisplayName("성공 - 게시물 Id로 검색하여 제목 수정 성공")
    @Test
    void updatePost_ShouldSuccess() {
      // given
      Post mockPost = new Post("beforeUpdate");
      String afterUpdateTitle = "afterUpdate";
      Long dummyId = 1L;
      given(postRepository.findFirstById(dummyId)).willReturn(mockPost);
      given(postRepository.existsByTitle(afterUpdateTitle)).willReturn(false);

      // when
      postService.updatePostTitle(dummyId, afterUpdateTitle);

      // then
      assertThat(mockPost.getTitle()).isEqualTo(afterUpdateTitle);
      verify(postRepository, times(1)).save(mockPost);
    }

    @DisplayName("실패 - 수정할 post가 존재하지 않음")
    @Test
    void updatePost_PostNotFound() {
      // given
      String afterUpdateTitle = "afterUpdate";
      Long dummyId = 1L;
      given(postRepository.findFirstById(dummyId)).willReturn(null);

      // when
      assertThatThrownBy(() -> postService.updatePostTitle(dummyId, afterUpdateTitle))
          .isInstanceOf(PostNotFoundException.class)
          .hasMessageContaining("존재하지 않는 게시물입니다.");

      // then
      verify(postRepository, times(0)).save(any());
    }

    @DisplayName("실패 - 중복된 제목")
    @Test
    void updatePost_DuplicateTitle() {
      // given
      String afterUpdateTitle = "afterUpdate";
      Post mockPost = new Post("beforeUpdate");
      Long dummyId = 1L;
      given(postRepository.findFirstById(dummyId)).willReturn(mockPost);
      given(postRepository.existsByTitle(afterUpdateTitle)).willReturn(true);

      // when
      assertThatThrownBy(() -> postService.updatePostTitle(dummyId, afterUpdateTitle))
          .isInstanceOf(RuntimeException.class)
          .hasMessageContaining("중복된 제목의 게시물입니다.");

      // then
      verify(postRepository, times(0)).save(any());
    }
  }
}

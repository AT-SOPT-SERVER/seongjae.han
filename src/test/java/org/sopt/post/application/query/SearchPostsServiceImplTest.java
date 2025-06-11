package org.sopt.post.application.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.post.application.dto.PostServiceRequestDto.SearchPostListServiceRequest;
import org.sopt.post.application.dto.PostServiceResponseDto.PostListServiceResponse;
import org.sopt.post.application.reader.PostReader;
import org.sopt.post.domain.Post;
import org.sopt.support.fixture.PostFixture;
import org.sopt.support.fixture.UserFixture;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;
import org.springframework.data.domain.PageImpl;


@ExtendWith(MockitoExtension.class)
@DisplayName("게시물 리스트 검색 서비스 테스트")
class SearchPostsServiceImplTest {

  @InjectMocks
  SearchPostsServiceImpl searchPostsService;

  @Mock
  private UserReader userReader;

  @Mock
  private PostReader postReader;

  private User user;
  private User writer;
  private Post post1;
  private Post post2;
  private Post post3;

  @BeforeEach
  void setUp() {
    user = UserFixture.create(1L);
    writer = UserFixture.create(2L);
    post1 = PostFixture.create(3L, writer);
    post2 = PostFixture.create(4L, writer);
    post3 = PostFixture.create(5L, writer);
  }

  @DisplayName("제목으로 검색 페이지네이션이 성공한다.")
  @Test
  void searchPosts_Success() {
    // given
    final SearchPostListServiceRequest serviceRequest = SearchPostListServiceRequest.of(
        null,
        null,
        null,
        "keyword",
        PostSearchSort.POST_TITLE);

    given(userReader.getUserOrThrow(1L)).willReturn(user);
    given(postReader.searchPosts(serviceRequest.toPageable(), serviceRequest.searchSort(),
        serviceRequest.keyword())).willReturn(new PageImpl<>(List.of(post1, post2, post3)));

    // when
    final PostListServiceResponse result = searchPostsService.execute(1L, serviceRequest);

    // then
    assertThat(result).isNotNull();
    assertThat(result.postHeaders()).hasSize(3);
    assertThat(result.pageSize()).isEqualTo(10);

    assertThat(result.postHeaders())
        .extracting("postId")
        .containsExactlyInAnyOrderElementsOf(
            List.of(3L, 4L, 5L)
        );
  }

  @Test
  @DisplayName("유저가 존재하지 않을 경우 검색에 실패한다.")
  void searchPosts_WhenUserNotExist_ThenFail() {
    // given
    final SearchPostListServiceRequest serviceRequest = SearchPostListServiceRequest.of(
        null,
        null,
        null,
        "keyword",
        PostSearchSort.POST_TITLE);

    given(userReader.getUserOrThrow(1L)).willThrow(new ApiException(ErrorCode.NOT_FOUND_USER));

    // when & then
    assertThatThrownBy(() -> searchPostsService.execute(1L, serviceRequest))
        .isInstanceOf(ApiException.class)
        .hasMessage(ErrorCode.NOT_FOUND_USER.getMessage());
  }

  @Test
  @DisplayName("검색 결과가 없으면 빈 리스트를 반환한다.")
  void searchPosts_WhenNoResult_ReturnsEmptyList() {
    // given
    final SearchPostListServiceRequest serviceRequest = SearchPostListServiceRequest.of(
        null,
        null,
        null,
        "keyword", PostSearchSort.POST_TITLE);

    given(userReader.getUserOrThrow(1L)).willReturn(user);
    given(postReader.searchPosts(serviceRequest.toPageable(), serviceRequest.searchSort(),
        serviceRequest.keyword())).willReturn(new PageImpl<>(List.of()));

    // when
    final PostListServiceResponse result = searchPostsService.execute(1L, serviceRequest);

    // then
    assertThat(result).isNotNull();
    assertThat(result.postHeaders()).isEmpty();
  }
}
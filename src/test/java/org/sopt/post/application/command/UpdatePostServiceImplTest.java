package org.sopt.post.application.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.post.application.reader.PostReader;
import org.sopt.post.domain.Post;
import org.sopt.post.application.dto.PostServiceRequestDto.UpdatePostServiceRequest;
import org.sopt.post.application.dto.PostServiceResponseDto.PostItemServiceResponse;
import org.sopt.support.fixture.PostFixture;
import org.sopt.support.fixture.UserFixture;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;

@ExtendWith(MockitoExtension.class)
@DisplayName("게시물 수정 서비스 테스트")
class UpdatePostServiceImplTest {

  @InjectMocks
  UpdatePostServiceImpl updatePostService;

  @Mock
  private PostReader postReader;
  @Mock
  private PostValidator postValidator;
  @Mock
  private UserReader userReader;

  private User anotherUser;
  private User writer;
  private Post post;

  @BeforeEach
  void setUp() {
    writer = UserFixture.create(1L);
    anotherUser = UserFixture.create(2L);
    post = PostFixture.create(3L, writer);
  }

  @DisplayName("게시물 수정을 성공한다.")
  @Test
  void updatePost_Success() {
    // given
    given(userReader.getUserOrThrow(1L)).willReturn(writer);
    given(postReader.getPostOrThrow(3L)).willReturn(post);
    willDoNothing()
        .given(postValidator).validateDuplicateTitle(any(String.class));

    final String title = "title";
    final String content = "b".repeat(200);
    final UpdatePostServiceRequest request = UpdatePostServiceRequest.of(3L, title, content);

    // when
    final PostItemServiceResponse result = updatePostService.execute(1L, request);

    // then
    assertThat(result).isNotNull();
    assertThat(result.title()).isEqualTo(title);
    assertThat(result.content()).isEqualTo(content);
    assertThat(result.writerName()).isEqualTo(writer.getName());
  }

  @DisplayName("유저가 존재하지 않을 시 게시물 수정에 실패한다.")
  @Test
  void updatePost_WhenUserNotExist_ThenFail() {
    // given
    given(userReader.getUserOrThrow(1L)).willThrow(new ApiException(ErrorCode.NOT_FOUND_USER));

    final String title = "title";
    final String content = "b".repeat(200);
    final UpdatePostServiceRequest request = UpdatePostServiceRequest.of(3L, title, content);

    // when & then
    assertThatThrownBy(() -> updatePostService.execute(1L, request))
        .isInstanceOf(ApiException.class)
        .hasMessage(ErrorCode.NOT_FOUND_USER.getMessage());
  }

  @DisplayName("게시물이 존재하지 않을 시 게시물 수정에 실패한다.")
  @Test
  void updatePost_WhenPostNotExist_ThenFail() {
    // given
    given(userReader.getUserOrThrow(1L)).willReturn(writer);
    given(postReader.getPostOrThrow(3L)).willThrow(new ApiException(ErrorCode.NOT_FOUND_POST));

    final String title = "title";
    final String content = "b".repeat(200);
    final UpdatePostServiceRequest request = UpdatePostServiceRequest.of(3L, title, content);

    // when & then
    assertThatThrownBy(() -> updatePostService.execute(1L, request))
        .isInstanceOf(ApiException.class)
        .hasMessage(ErrorCode.NOT_FOUND_POST.getMessage());
  }

  @DisplayName("작성자와 유저가 다를 경우 게시물 수정에 실패한다.")
  @Test
  void updatePost_WhenUserDoesNotOwnPost_ThenFail() {
    // given
    given(userReader.getUserOrThrow(2L)).willReturn(anotherUser);
    given(postReader.getPostOrThrow(3L)).willReturn(post);

    final String title = "title";
    final String content = "b".repeat(200);
    final UpdatePostServiceRequest request = UpdatePostServiceRequest.of(3L, title, content);

    // when & then
    assertThatThrownBy(() -> updatePostService.execute(2L, request))
        .isInstanceOf(ApiException.class)
        .hasMessage(ErrorCode.USER_NOT_PERMITTED.getMessage());
  }

  @DisplayName("제목이 중복일 경우 게시물 수정에 실패한다.")
  @Test
  void updatePost_WhenTitleDuplicates_ThenFail() {
    // given
    given(userReader.getUserOrThrow(1L)).willReturn(writer);
    given(postReader.getPostOrThrow(3L)).willReturn(post);
    willThrow(new ApiException(ErrorCode.DUPLICATE_POST_TITLE))
        .given(postValidator).validateDuplicateTitle(any(String.class));

    final String title = "title";
    final String content = "b".repeat(200);
    final UpdatePostServiceRequest request = UpdatePostServiceRequest.of(3L, title, content);

    // when & then
    assertThatThrownBy(() -> updatePostService.execute(1L, request))
        .isInstanceOf(ApiException.class)
        .hasMessage(ErrorCode.DUPLICATE_POST_TITLE.getMessage());
  }
}
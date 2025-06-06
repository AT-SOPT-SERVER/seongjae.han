package org.sopt.post.application.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.never;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.global.util.TimeIntervalUtil;
import org.sopt.post.application.writer.PostWriter;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.PostServiceRequestDto.CreatePostServiceRequest;
import org.sopt.post.dto.PostServiceResponseDto.PostItemServiceResponse;
import org.sopt.support.fixture.PostFixture;
import org.sopt.support.fixture.UserFixture;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;

@ExtendWith(MockitoExtension.class)
@DisplayName("게시물 작성 서비스 테스트")
class CreatePostServiceImplTest {

  @InjectMocks
  CreatePostServiceImpl createPostService;

  @Mock
  private UserReader userReader;

  @Mock
  private TimeIntervalUtil timeIntervalUtil;

  @Mock
  private PostWriter postWriter;

  @Mock
  private PostValidator postValidator;

  private User user;
  private User writer;
  private Post post;

  @BeforeEach
  void setUp() {
    user = UserFixture.create(1L);
    writer = UserFixture.create(2L);
    post = PostFixture.create(3L, writer);
  }

  @DisplayName("게시물 작성을 성공한다.")
  @Test
  void createPost_Success() {
    // given
    given(userReader.getUserOrThrow(1L)).willReturn(writer);
    given(timeIntervalUtil.isAvailable()).willReturn(true);
    willDoNothing()
        .given(postValidator).validateDuplicateTitle(any(String.class));
    given(postWriter.save(any(Post.class))).willAnswer(
        invocationOnMock -> invocationOnMock.getArgument(0));
    willDoNothing()
        .given(timeIntervalUtil).startTimer();

    final String content = "a".repeat(100);
    final CreatePostServiceRequest request = CreatePostServiceRequest.of("title", content);

    // when
    final PostItemServiceResponse result = createPostService.execute(1L, request);

    // then
    // String title, String content, String writerName
    assertThat(result).isNotNull();
    assertThat(result.title()).isEqualTo("title");
    assertThat(result.content()).isEqualTo(content);
    assertThat(result.writerName()).isEqualTo(writer.getName());
  }

  @DisplayName("유저가 존재하지 않을 시 게시물 작성을 실패한다.")
  @Test
  void createPost_WhenUserNotExist_ThenFail() {
    // given
    given(userReader.getUserOrThrow(1L)).willThrow(new ApiException(ErrorCode.NOT_FOUND_USER));

    final String content = "a".repeat(100);
    final CreatePostServiceRequest request = CreatePostServiceRequest.of("title", content);

    // when & then
    assertThatThrownBy(() -> createPostService.execute(1L, request))
        .isInstanceOf(ApiException.class)
        .hasMessage(ErrorCode.NOT_FOUND_USER.getMessage());
    then(postWriter).should(never()).save(any());
    then(timeIntervalUtil).should(never()).startTimer();

  }

  @DisplayName("게시물 작성 쿨타임이 다 돌지 않은 경우 게시물 작성에 실패한다.")
  @Test
  void createPost_WhenPostCoolDown_ThenFail() {
    // given
    given(userReader.getUserOrThrow(1L)).willReturn(writer);
    given(timeIntervalUtil.isAvailable()).willReturn(false);

    final String content = "a".repeat(100);
    final CreatePostServiceRequest request = CreatePostServiceRequest.of("title", content);

    // when & then
    assertThatThrownBy(() -> createPostService.execute(1L, request))
        .isInstanceOf(ApiException.class)
        .hasMessage(ErrorCode.TOO_MANY_POST_REQUESTS.getMessage());
    then(postWriter).should(never()).save(any());
    then(timeIntervalUtil).should(never()).startTimer();
  }

  @DisplayName("제목이 중복일 경우 게시물 작성에 실패한다.")
  @Test
  void createPost_WhenTitleDuplicates_ThenFail() {
    // given
    given(userReader.getUserOrThrow(1L)).willReturn(writer);
    given(timeIntervalUtil.isAvailable()).willReturn(true);
    willThrow(new ApiException(ErrorCode.DUPLICATE_POST_TITLE))
        .given(postValidator).validateDuplicateTitle(any(String.class));

    final String content = "a".repeat(100);
    final CreatePostServiceRequest request = CreatePostServiceRequest.of("title", content);

    // when & then
    assertThatThrownBy(() -> createPostService.execute(1L, request))
        .isInstanceOf(ApiException.class)
        .hasMessage(ErrorCode.DUPLICATE_POST_TITLE.getMessage());
    then(postWriter).should(never()).save(any());
    then(timeIntervalUtil).should(never()).startTimer();
  }
}
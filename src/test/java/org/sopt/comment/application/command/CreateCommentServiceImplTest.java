package org.sopt.comment.application.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sopt.comment.application.writer.CommentWriter;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.application.dto.CommentServiceRequestDto.CommentCreateServiceRequestDto;
import org.sopt.comment.application.dto.CommentServiceResponseDto.CommentItemDto;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.post.application.reader.PostReader;
import org.sopt.post.domain.Post;
import org.sopt.support.fixture.PostFixture;
import org.sopt.support.fixture.UserFixture;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;

@ExtendWith(MockitoExtension.class)
class CreateCommentServiceImplTest {

  @InjectMocks
  CreateCommentServiceImpl createCommentService;

  @Mock
  private PostReader postReader;
  @Mock
  private UserReader userReader;
  @Mock
  private CommentWriter commentWriter;

  @DisplayName("댓글 생성 성공")
  @Test
  void createComment_Success() {
    // given
    Long userId = 2L;
    Long postId = 1L;
    final String content = "a".repeat(200);

    final User user = UserFixture.create(userId);
    final Post post = PostFixture.create(postId, user);

    given(userReader.getUserOrThrow(userId)).willReturn(user);
    given(postReader.getPostOrThrow(postId)).willReturn(post);
    given(commentWriter.save(any(Comment.class))).willAnswer(
        invocationOnMock -> invocationOnMock.getArgument(0));

    CommentCreateServiceRequestDto dto = CommentCreateServiceRequestDto.of(userId, postId, content);

    // when
    final CommentItemDto result = createCommentService.execute(dto);

    // then
    assertThat(result).isNotNull();
    assertThat(result.postId()).isEqualTo(postId);
    assertThat(result.content()).isEqualTo(content);
    assertThat(result.userDto().userId()).isEqualTo(userId);
  }

  @DisplayName("유저가 존재하지 않을 시 댓글 생성에 실패한다.")
  @Test
  void createComment_WhenUserNotExist_ThenFail() {
    // given
    Long userId = 2L;
    Long postId = 1L;
    final String content = "a".repeat(200);

    given(userReader.getUserOrThrow(userId)).willThrow(new ApiException(ErrorCode.NOT_FOUND_USER));

    CommentCreateServiceRequestDto dto = CommentCreateServiceRequestDto.of(userId, postId, content);

    // when & then
    assertThatThrownBy(() -> createCommentService.execute(dto))
        .isInstanceOf(ApiException.class)
        .hasMessage(ErrorCode.NOT_FOUND_USER.getMessage());
    then(userReader).should().getUserOrThrow(userId);
    then(postReader).should(never()).getPostOrThrow(any());
    then(commentWriter).should(never()).save(any());
  }

  @DisplayName("게시글이 존재하지 않을 시 댓글 생성에 실패한다.")
  @Test
  void createComment_WhenPostNotExist_ThenFail() {
    // given
    Long userId = 2L;
    Long postId = 1L;
    final String content = "a".repeat(200);

    final User user = UserFixture.create(userId);

    given(userReader.getUserOrThrow(userId)).willReturn(user);
    given(postReader.getPostOrThrow(postId)).willThrow(new ApiException(ErrorCode.NOT_FOUND_POST));

    CommentCreateServiceRequestDto dto = CommentCreateServiceRequestDto.of(userId, postId, content);

    // when & then
    assertThatThrownBy(() -> createCommentService.execute(dto))
        .isInstanceOf(ApiException.class)
        .hasMessage(ErrorCode.NOT_FOUND_POST.getMessage());
    then(userReader).should().getUserOrThrow(userId);
    then(postReader).should().getPostOrThrow(postId);
    then(commentWriter).should(never()).save(any());

  }

  @DisplayName("댓글 길이가 너무 긴경우 댓글 생성에 실패한다.")
  @Test
  void createComment_WhenCommentTooLong_ThenFail() {
    // given
    Long userId = 2L;
    Long postId = 1L;
    final String content = "a".repeat(301);

    final User user = UserFixture.create(userId);
    final Post post = PostFixture.create(postId, user);

    given(userReader.getUserOrThrow(userId)).willReturn(user);
    given(postReader.getPostOrThrow(postId)).willReturn(post);

    CommentCreateServiceRequestDto dto = CommentCreateServiceRequestDto.of(userId, postId, content);

    // when & then
    assertThatThrownBy(() -> createCommentService.execute(dto))
        .isInstanceOf(ApiException.class)
        .hasMessage(ErrorCode.TOO_LONG_COMMENT_CONTENT.getMessage());
    then(userReader).should().getUserOrThrow(userId);
    then(postReader).should().getPostOrThrow(postId);
    then(commentWriter).should(never()).save(any());

  }
}
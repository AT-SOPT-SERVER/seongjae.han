package org.sopt.comment.application.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sopt.comment.application.reader.CommentReader;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.dto.CommentRequestDto.CommentUpdateRequestDto;
import org.sopt.comment.dto.CommentResponseDto.CommentItemDto;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.post.domain.Post;
import org.sopt.support.fixture.CommentFixture;
import org.sopt.support.fixture.PostFixture;
import org.sopt.support.fixture.UserFixture;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;

@ExtendWith(MockitoExtension.class)
class UpdateCommentServiceImplTest {

  @InjectMocks
  UpdateCommentServiceImpl updateCommentService;

  @Mock
  private CommentReader commentReader;
  @Mock
  private UserReader userReader;

  @DisplayName("댓글 수정에 성공한다.")
  @Test
  void updateComment_Success() {
    // given
    Long userId = 1L;
    Long writerId = 1L;
    Long postId = 2L;
    Long commentId = 3L;
    final User user = UserFixture.create(userId);
    final User writer = UserFixture.create(writerId);
    final Post post = PostFixture.create(postId, user);
    final String content = "a".repeat(300);
    final Comment comment = CommentFixture.create(commentId, content, writer, post);

    given(userReader.getUserOrThrow(userId)).willReturn(user);
    given(commentReader.getCommentOrThrow(commentId)).willReturn(comment);

    final CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDto.of(
        userId, commentId, "b".repeat(300));

    // when
    final CommentItemDto result = updateCommentService.execute(commentUpdateRequestDto);

    // then
    assertThat(result).isNotNull();
    assertThat(result.postId()).isEqualTo(postId);
    assertThat(result.content()).isEqualTo("b".repeat(300));
    assertThat(result.userDto().userId()).isEqualTo(userId);
  }

  @DisplayName("유저가 존재하지 않을 시 댓글 수정에 실패한다.")
  @Test
  void updateComment_WhenUserNotExist_ThenFail() {
    // given
    Long userId = 1L;
    Long writerId = 1L;
    Long postId = 2L;
    Long commentId = 3L;
    final User user = UserFixture.create(userId);
    final User writer = UserFixture.create(writerId);
    final Post post = PostFixture.create(postId, user);
    final String content = "a".repeat(300);
    final Comment comment = CommentFixture.create(commentId, content, writer, post);

    given(userReader.getUserOrThrow(userId)).willThrow(new ApiException(ErrorCode.NOT_FOUND_USER));

    final CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDto.of(
        userId, commentId, "b".repeat(300));

    // when & then
    assertThatThrownBy(() -> updateCommentService.execute(commentUpdateRequestDto))
        .isInstanceOf(ApiException.class)
        .hasMessage(ErrorCode.NOT_FOUND_USER.getMessage());
  }

  @DisplayName("댓글 작성자와 유저가 일치하지 않을 시 댓글 수정에 실패한다.")
  @Test
  void updateComment_WhenCommentOwnershipInvalidate_ThenFail() {
    // given
    Long userId = 1L;
    Long writerId = 2L;
    Long postId = 2L;
    Long commentId = 3L;
    final User user = UserFixture.create(userId);
    final User writer = UserFixture.create(writerId);
    final Post post = PostFixture.create(postId, user);
    final String content = "a".repeat(300);
    final Comment comment = CommentFixture.create(commentId, content, writer, post);

    given(userReader.getUserOrThrow(userId)).willReturn(user);
    given(commentReader.getCommentOrThrow(commentId)).willReturn(comment);

    final CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDto.of(
        userId, commentId, "b".repeat(300));

    // when & then
    assertThatThrownBy(() -> updateCommentService.execute(commentUpdateRequestDto))
        .isInstanceOf(ApiException.class)
        .hasMessage(ErrorCode.USER_NOT_PERMITTED.getMessage());
  }

  @DisplayName("댓글 길이가 너무 긴 경우 댓글 수정에 실패한다.")
  @Test
  void updateComment_WhenCommentTooLong_ThenFail() {
    // given
    Long userId = 1L;
    Long writerId = 1L;
    Long postId = 2L;
    Long commentId = 3L;
    final User user = UserFixture.create(userId);
    final User writer = UserFixture.create(writerId);
    final Post post = PostFixture.create(postId, user);
    final String content = "a".repeat(300);
    final Comment comment = CommentFixture.create(commentId, content, writer, post);

    given(userReader.getUserOrThrow(userId)).willReturn(user);
    given(commentReader.getCommentOrThrow(commentId)).willReturn(comment);

    final CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDto.of(
        userId, commentId, "b".repeat(301));

    // when & then
    assertThatThrownBy(() -> updateCommentService.execute(commentUpdateRequestDto))
        .isInstanceOf(ApiException.class)
        .hasMessage(ErrorCode.TOO_LONG_COMMENT_CONTENT.getMessage());
  }
}
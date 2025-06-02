package org.sopt.comment.application.command;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sopt.comment.application.reader.CommentReader;
import org.sopt.comment.application.writer.CommentWriter;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.dto.CommentRequestDto.CommentDeleteRequestDto;
import org.sopt.comment.dto.CommentRequestDto.CommentUpdateRequestDto;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.post.domain.Post;
import org.sopt.support.fixture.CommentFixture;
import org.sopt.support.fixture.PostFixture;
import org.sopt.support.fixture.UserFixture;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;

@ExtendWith(MockitoExtension.class)
class DeleteCommentServiceImplTest {

  @InjectMocks
  DeleteCommentServiceImpl deleteCommentService;

  @Mock
  private CommentReader commentReader;
  @Mock
  private UserReader userReader;
  @Mock
  private CommentWriter commentWriter;

  @DisplayName("댓글 삭제에 성공한다.")
  @Test
  void deleteComment_Success() {
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

    final CommentDeleteRequestDto commentDeleteRequestDto = CommentDeleteRequestDto.of(userId,
        commentId);

    // When & Then
    assertThatCode(() -> deleteCommentService.execute(commentDeleteRequestDto))
        .doesNotThrowAnyException();

    // Then
    then(commentReader).should().getCommentOrThrow(commentId);
    then(userReader).should().getUserOrThrow(userId);
    then(commentWriter).should().delete(comment);
  }

  @DisplayName("유저가 존재하지 않을 시 댓글 삭제에 실패한다.")
  @Test
  void deleteComment_WhenUserNotExist_ThenFail() {
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

    given(userReader.getUserOrThrow(userId))
        .willThrow(new ApiException(ErrorCode.NOT_FOUND_USER));

    final CommentDeleteRequestDto commentDeleteRequestDto = CommentDeleteRequestDto.of(userId,
        commentId);

    // When & Then
    assertThatThrownBy(() -> deleteCommentService.execute(commentDeleteRequestDto))
        .isInstanceOf(ApiException.class)
        .hasMessage(ErrorCode.NOT_FOUND_USER.getMessage());
  }

  @DisplayName("댓글이 존재하지 않을 시 댓글 삭제에 실패한다.")
  @Test
  void deleteComment_WhenCommentNotExist_ThenFail() {
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

    given(commentReader.getCommentOrThrow(commentId))
        .willThrow(new ApiException(ErrorCode.NOT_FOUND_COMMENT));

    final CommentDeleteRequestDto commentDeleteRequestDto = CommentDeleteRequestDto.of(userId,
        commentId);

    // When & Then
    assertThatThrownBy(() -> deleteCommentService.execute(commentDeleteRequestDto))
        .isInstanceOf(ApiException.class)
        .hasMessage(ErrorCode.NOT_FOUND_COMMENT.getMessage());
  }

  @DisplayName("댓글 작성자와 유저가 일치하지 않을 시 댓글 삭제에 실패한다.")
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

    final CommentDeleteRequestDto commentDeleteRequestDto = CommentDeleteRequestDto.of(userId,
        commentId);

    // When & Then
    assertThatThrownBy(() -> deleteCommentService.execute(commentDeleteRequestDto))
        .isInstanceOf(ApiException.class)
        .hasMessage(ErrorCode.USER_NOT_PERMITTED.getMessage());
  }
}
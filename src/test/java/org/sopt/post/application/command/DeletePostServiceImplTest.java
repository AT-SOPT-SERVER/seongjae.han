package org.sopt.post.application.command;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;

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
import org.sopt.like.application.reader.LikeReader;
import org.sopt.like.application.writer.LikeWriter;
import org.sopt.post.application.reader.PostReader;
import org.sopt.post.application.writer.PostWriter;
import org.sopt.post.domain.Post;
import org.sopt.support.fixture.PostFixture;
import org.sopt.support.fixture.UserFixture;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;

@ExtendWith(MockitoExtension.class)
@DisplayName("게시물 삭제 서비스 테스트")
class DeletePostServiceImplTest {

  @InjectMocks
  DeletePostServiceImpl deletePostService;

  @Mock
  private PostReader postReader;
  @Mock
  private PostWriter postWriter;
  @Mock
  private UserReader userReader;

  @Mock
  private LikeReader likeReader;

  @Mock
  private LikeWriter likeWriter;

  private User anotherUser;
  private User writer;
  private Post post;

  @BeforeEach
  void setUp() {
    writer = UserFixture.create(1L);
    anotherUser = UserFixture.create(2L);
    post = PostFixture.create(3L, writer);
  }

  @DisplayName("게시물 삭제를 성공한다.")
  @Test
  void deletePost_Success() {
    // given
    given(userReader.getUserOrThrow(1L)).willReturn(writer);
    given(postReader.getPostOrThrow(3L)).willReturn(post);
    given(likeReader.getByPostId(3L)).willReturn(List.of());
    willDoNothing()
        .given(likeWriter).deleteBatch(anyList());

    // when
    assertThatCode(() -> deletePostService.execute(1L, 3L))
        .doesNotThrowAnyException();

    then(postWriter).should().delete(post);
  }

  @DisplayName("유저가 존재하지 않을 시 게시물 삭제에 실패한다.")
  @Test
  void deletePost_WhenUserNotExist_ThenFail() {
    // given
    given(userReader.getUserOrThrow(1L)).willThrow(new ApiException(ErrorCode.NOT_FOUND_USER));

    // when & then
    assertThatThrownBy(() -> deletePostService.execute(1L, 3L))
        .isInstanceOf(ApiException.class)
        .hasMessage(ErrorCode.NOT_FOUND_USER.getMessage());
  }

  @DisplayName("게시물이 존재하지 않을 시 게시물 삭제에 실패한다.")
  @Test
  void deletePost_WhenPostNotExist_ThenFail() {
    // given
    given(userReader.getUserOrThrow(1L)).willReturn(writer);
    given(postReader.getPostOrThrow(3L)).willThrow(new ApiException(ErrorCode.NOT_FOUND_POST));

    // when & then
    assertThatThrownBy(() -> deletePostService.execute(1L, 3L))
        .isInstanceOf(ApiException.class)
        .hasMessage(ErrorCode.NOT_FOUND_POST.getMessage());
  }

  @DisplayName("작성자와 유저가 다를 경우 게시물 삭제에 실패한다.")
  @Test
  void deletePost_WhenUserDoesNotOwnPost_ThenFail() {
    // given
    given(userReader.getUserOrThrow(2L)).willReturn(anotherUser);
    given(postReader.getPostOrThrow(3L)).willReturn(post);

    // when & then
    assertThatThrownBy(() -> deletePostService.execute(2L, 3L))
        .isInstanceOf(ApiException.class)
        .hasMessage(ErrorCode.USER_NOT_PERMITTED.getMessage());
  }
}
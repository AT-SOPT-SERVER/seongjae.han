package org.sopt.like.application.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sopt.comment.application.reader.CommentReader;
import org.sopt.comment.domain.Comment;
import org.sopt.like.application.dto.LikeServiceRequestDto.CreateCommentLikeServiceRequest;
import org.sopt.like.application.dto.LikeServiceResponseDto.CreateCommentLikeServiceResponse;
import org.sopt.like.application.reader.LikeReader;
import org.sopt.like.application.writer.LikeWriter;
import org.sopt.like.domain.Like;
import org.sopt.post.domain.Post;
import org.sopt.support.fixture.CommentFixture;
import org.sopt.support.fixture.LikeFixture;
import org.sopt.support.fixture.PostFixture;
import org.sopt.support.fixture.UserFixture;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@DisplayName("댓글 좋아요 테스트")
class CreateCommentLikeServiceImplTest {

  @InjectMocks
  CreateCommentLikeServiceImpl createCommentLikeService;

  @Mock
  private CommentReader commentReader;
  @Mock
  private UserReader userReader;
  @Mock
  private LikeReader likeReader;
  @Mock
  private LikeWriter likeWriter;

  private User user1;
  private User user2;
  private Post post;
  private Like like;
  private Comment comment;

  @BeforeEach
  void setUp() {
    // given: 테스트용 데이터 준비
    user1 = createUser(1L);
    user2 = createUser(2L);
    post = createPost(3L, user1);
    like = createPostLike(4L, post, user1);
    comment = createComment(5L);
  }

  @Test
  @DisplayName("댓글 좋아요에 성공한다.")
  void createPostLike_Success() {
    // given
    final long postId = 3L;
    given(userReader.getUserOrThrow(1L)).willReturn(user1);
    given(commentReader.getCommentOrThrow(5L)).willReturn(comment);

    given(likeWriter.save(any(Like.class))).willAnswer(
        invocationOnMock -> invocationOnMock.getArgument(0));
    final CreateCommentLikeServiceRequest createCommentLikeServiceRequest = CreateCommentLikeServiceRequest.of(
        5L);

    // when
    final CreateCommentLikeServiceResponse result = createCommentLikeService.execute(1L,
        createCommentLikeServiceRequest);

    // then
    assertThat(result).isNotNull();
    assertThat(result.commentId()).isEqualTo(5L);
    assertThat(result.userId()).isEqualTo(1L);
  }

  private Post createPost(Long id, User user) {
    return PostFixture.create(id, user);
  }

  private User createUser(Long id) {
    return UserFixture.create(id);
  }

  private Like createPostLike(final long likeId, final Post post, final User user) {
    return LikeFixture.createPostLike(likeId, post, user);
  }

  private Comment createComment(long commentId) {
    final Comment comment = CommentFixture.create("test", user1, post);
    ReflectionTestUtils.setField(comment, "id", commentId);

    return comment;
  }
}
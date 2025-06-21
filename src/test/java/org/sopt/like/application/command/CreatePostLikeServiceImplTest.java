package org.sopt.like.application.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sopt.comment.application.dto.CommentServiceRequestDto.CommentListServiceRequestDto;
import org.sopt.like.application.dto.LikeServiceRequestDto.CreatePostLikeServiceRequest;
import org.sopt.like.application.dto.LikeServiceResponseDto.CreatePostLikeServiceResponse;
import org.sopt.like.application.writer.LikeWriter;
import org.sopt.like.domain.Like;
import org.sopt.post.domain.Post;
import org.sopt.support.fixture.LikeFixture;
import org.sopt.support.fixture.PostFixture;
import org.sopt.support.fixture.UserFixture;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@ExtendWith(MockitoExtension.class)
@DisplayName("게시글 좋아요 테스트")
class CreatePostLikeServiceImplTest {

  @InjectMocks
  CreatePostLikeServiceImpl createPostLikeService;

  @Mock
  private LikeWriter likeWriter;
  @Mock
  private UserReader userReader;
  @Mock
  private LikeValidator likeValidator;
  private User user1;
  private User user2;
  private Post post;
  private Like like;

  @BeforeEach
  void setUp() {
    // given: 테스트용 데이터 준비
    user1 = createUser(1L);
    user2 = createUser(2L);
    post = createPost(3L, user1);
    like = createPostLike(4L, post, user1);
  }

  @Test
  @DisplayName("게시글 좋아요에 성공한다.")
  void createPostLike_Success() {
    // given
    final long postId = 3L;
    given(userReader.getUserOrThrow(1L)).willReturn(user1);
    willDoNothing().given(likeValidator).validatePostLikeDuplicate(user1, postId);
    willDoNothing().given(likeValidator).validatePostExists(postId);

    given(likeWriter.save(any(Like.class))).willAnswer(
        invocationOnMock -> invocationOnMock.getArgument(0));
    final CreatePostLikeServiceRequest createPostLikeServiceRequest = CreatePostLikeServiceRequest.of(
        postId);

    // when
    final CreatePostLikeServiceResponse result = createPostLikeService.execute(1L,
        createPostLikeServiceRequest);

    // then
    assertThat(result).isNotNull();
    assertThat(result.postId()).isEqualTo(3L);
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

}
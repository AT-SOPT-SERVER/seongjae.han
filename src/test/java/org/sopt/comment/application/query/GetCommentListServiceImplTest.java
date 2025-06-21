package org.sopt.comment.application.query;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sopt.comment.application.reader.CommentReader;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.application.dto.CommentServiceRequestDto.CommentListServiceRequestDto;
import org.sopt.comment.application.dto.CommentServiceResponseDto.CommentItemDto;
import org.sopt.comment.application.dto.CommentServiceResponseDto.CommentPageListDto;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.post.application.reader.PostReader;
import org.sopt.post.domain.Post;
import org.sopt.support.fixture.CommentFixture;
import org.sopt.support.fixture.PostFixture;
import org.sopt.support.fixture.UserFixture;
import org.sopt.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetCommentListServiceImpl 테스트")
class GetCommentListServiceImplTest {

  @Mock
  private CommentReader commentReader;

  @Mock
  private PostReader postReader;

  @InjectMocks
  private GetCommentListServiceImpl getCommentListService;

  private Post post;
  private User user1;
  private User user2;
  private Comment comment1;
  private Comment comment2;
  private Comment comment3;
  private CommentListServiceRequestDto basicRequest;
  private Pageable expectedPageable;

  @BeforeEach
  void setUp() {
    // given: 테스트용 데이터 준비
    user1 = createUser(1L);
    user2 = createUser(2L);
    post = createPost(1L, user1);

    LocalDateTime now = LocalDateTime.now();
    comment1 = createComment(1L, "첫 번째 댓글", user1, post, now.minusHours(2));
    comment2 = createComment(2L, "두 번째 댓글", user2, post, now.minusHours(1));
    comment3 = createComment(3L, "세 번째 댓글", user1, post, now);

    basicRequest = new CommentListServiceRequestDto(1L, 0, 20, "asc");
    expectedPageable = PageRequest.of(0, 20, Sort.by(Direction.ASC, "createdAt"));
  }

  @Test
  @DisplayName("댓글 리스트 조회가 성공하면 페이징된 댓글 목록을 반환한다")
  void getCommentList_whenSuccessful_thenReturnsPagedComments() {
    // given
    List<Comment> comments = List.of(comment1, comment2, comment3);
    Page<Comment> commentPage = new PageImpl<>(comments, expectedPageable, 3);

    given(postReader.getPostOrThrow(1L)).willReturn(post);
    given(commentReader.getCommentsByPostId(1L, expectedPageable)).willReturn(commentPage);

    // when
    CommentPageListDto result = getCommentListService.execute(basicRequest);

    // then
    assertThat(result).isNotNull();
    assertThat(result.comments()).hasSize(3);
    assertThat(result.totalElements()).isEqualTo(3);
    assertThat(result.totalPages()).isEqualTo(1);
    assertThat(result.currentPage()).isEqualTo(0);
    assertThat(result.pageSize()).isEqualTo(20);
    assertThat(result.hasNext()).isFalse();
    assertThat(result.hasPrevious()).isFalse();

    // 댓글 내용 검증
    List<CommentItemDto> commentDtos = result.comments();
    assertThat(commentDtos.get(0).commentId()).isEqualTo(1L);
    assertThat(commentDtos.get(0).content()).isEqualTo("첫 번째 댓글");
    assertThat(commentDtos.get(0).userDto().userId()).isEqualTo(1L);

    // 의존성 호출 검증
    then(postReader).should().getPostOrThrow(1L);
    then(commentReader).should().getCommentsByPostId(1L, expectedPageable);
  }

  @Test
  @DisplayName("내림차순 정렬 요청시 최신 댓글부터 반환한다")
  void getCommentList_whenDescOrder_thenReturnsCommentsInDescOrder() {
    // given
    CommentListServiceRequestDto descRequest = new CommentListServiceRequestDto(1L, 0, 20, "desc");
    Pageable descPageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "createdAt"));

    List<Comment> comments = List.of(comment3, comment2, comment1); // 최신순
    Page<Comment> commentPage = new PageImpl<>(comments, descPageable, 3);

    given(postReader.getPostOrThrow(1L)).willReturn(post);
    given(commentReader.getCommentsByPostId(1L, descPageable)).willReturn(commentPage);

    // when
    CommentPageListDto result = getCommentListService.execute(descRequest);

    // then
    assertThat(result.comments()).hasSize(3);
    assertThat(result.comments().get(0).commentId()).isEqualTo(3L); // 최신 댓글이 첫 번째
    assertThat(result.comments().get(2).commentId()).isEqualTo(1L); // 가장 오래된 댓글이 마지막

    then(commentReader).should().getCommentsByPostId(1L, descPageable);
  }

  @Test
  @DisplayName("두 번째 페이지 조회시 올바른 페이징 정보를 반환한다")
  void getCommentList_whenSecondPage_thenReturnsCorrectPagingInfo() {
    // given
    CommentListServiceRequestDto secondPageRequest = new CommentListServiceRequestDto(1L, 1, 2, "asc");
    Pageable secondPagePageable = PageRequest.of(1, 2, Sort.by(Sort.Direction.ASC, "createdAt"));

    List<Comment> secondPageComments = List.of(comment3);
    Page<Comment> commentPage = new PageImpl<>(secondPageComments, secondPagePageable, 3);

    given(postReader.getPostOrThrow(1L)).willReturn(post);
    given(commentReader.getCommentsByPostId(1L, secondPagePageable)).willReturn(commentPage);

    // when
    CommentPageListDto result = getCommentListService.execute(secondPageRequest);

    // then
    assertThat(result.comments()).hasSize(1);
    assertThat(result.totalElements()).isEqualTo(3);
    assertThat(result.totalPages()).isEqualTo(2);
    assertThat(result.currentPage()).isEqualTo(1);
    assertThat(result.pageSize()).isEqualTo(2);
    assertThat(result.hasNext()).isFalse();
    assertThat(result.hasPrevious()).isTrue();
  }

  @Test
  @DisplayName("댓글이 없는 게시글 조회시 빈 리스트를 반환한다")
  void getCommentList_whenNoComments_thenReturnsEmptyList() {
    // given
    Page<Comment> emptyPage = new PageImpl<>(List.of(), expectedPageable, 0);

    given(postReader.getPostOrThrow(1L)).willReturn(post);
    given(commentReader.getCommentsByPostId(1L, expectedPageable)).willReturn(emptyPage);

    // when
    CommentPageListDto result = getCommentListService.execute(basicRequest);

    // then
    assertThat(result.comments()).isEmpty();
    assertThat(result.totalElements()).isEqualTo(0);
    assertThat(result.totalPages()).isEqualTo(0);
    assertThat(result.hasNext()).isFalse();
    assertThat(result.hasPrevious()).isFalse();
  }

  @Test
  @DisplayName("존재하지 않는 게시글 조회시 예외가 발생한다")
  void getCommentList_whenPostNotFound_thenThrowsException() {
    // given
    CommentListServiceRequestDto invalidRequest = new CommentListServiceRequestDto(999L, 0, 20, "asc");
    given(postReader.getPostOrThrow(999L))
        .willThrow(new ApiException(ErrorCode.NOT_FOUND_POST));

    // when & then
    assertThatThrownBy(() -> getCommentListService.execute(invalidRequest))
        .isInstanceOf(ApiException.class)
        .extracting(ex -> ((ApiException) ex).getErrorCode())
        .isEqualTo(ErrorCode.NOT_FOUND_POST);

    // CommentReader는 호출되지 않아야 함
    then(commentReader).should(never()).getCommentsByPostId(anyLong(), any(Pageable.class));
  }

  @Test
  @DisplayName("null 요청시 예외가 발생한다")
  void getCommentList_whenNullRequest_thenThrowsException() {
    // when & then
    assertThatThrownBy(() -> getCommentListService.execute(null))
        .isInstanceOf(NullPointerException.class);

    // 어떤 의존성도 호출되지 않아야 함
    then(postReader).should(never()).getPostOrThrow(anyLong());
    then(commentReader).should(never()).getCommentsByPostId(anyLong(), any(Pageable.class));
  }

  @Test
  @DisplayName("기본 생성자로 생성된 요청은 기본값을 적용한다")
  void getCommentList_whenDefaultRequest_thenAppliesDefaultValues() {
    // given
    CommentListServiceRequestDto defaultRequest = new CommentListServiceRequestDto(
        1L); // 기본값: page=0, size=10, sort=asc
    Pageable defaultPageable = PageRequest.of(0, 10, Sort.by(Direction.DESC, "createdAt"));

    List<Comment> comments = List.of(comment1);
    Page<Comment> commentPage = new PageImpl<>(comments, defaultPageable, 1);

    given(postReader.getPostOrThrow(1L)).willReturn(post);
    given(commentReader.getCommentsByPostId(1L, defaultPageable)).willReturn(commentPage);

    // when
    CommentPageListDto result = getCommentListService.execute(defaultRequest);

    // then
    assertThat(result).isNotNull();
    assertThat(result.currentPage()).isEqualTo(0);
    assertThat(result.pageSize()).isEqualTo(10);

    then(commentReader).should().getCommentsByPostId(1L, defaultPageable);
  }

  @Test
  @DisplayName("잘못된 정렬 방향 입력시 기본값(desc)을 적용한다")
  void getCommentList_whenInvalidSortDirection_thenAppliesDefaultSort() {
    // given
    CommentListServiceRequestDto invalidSortRequest = new CommentListServiceRequestDto(1L, 0, 20, "invalid");
    Pageable defaultPageable = PageRequest.of(0, 20, Sort.by(Direction.DESC, "createdAt"));

    List<Comment> comments = List.of(comment1);
    Page<Comment> commentPage = new PageImpl<>(comments, defaultPageable, 1);

    given(postReader.getPostOrThrow(1L)).willReturn(post);
    given(commentReader.getCommentsByPostId(1L, defaultPageable)).willReturn(commentPage);

    // when
    CommentPageListDto result = getCommentListService.execute(invalidSortRequest);

    // then
    assertThat(result).isNotNull();
    then(commentReader).should().getCommentsByPostId(1L, defaultPageable);
  }

  // 테스트 헬퍼 메서드들
  private Post createPost(Long id, User user) {
    return PostFixture.create(id, user);
  }

  private User createUser(Long id) {
    return UserFixture.create(id);
  }

  private Comment createComment(Long id, String content, User user, Post post,
      LocalDateTime createdAt) {
    final Comment comment = CommentFixture.create(id, content, user, post);
    ReflectionTestUtils.setField(comment, "createdAt", createdAt);

    return comment;
  }
}
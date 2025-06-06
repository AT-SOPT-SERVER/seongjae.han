package org.sopt.post.application.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.post.application.reader.PostReader;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.PostServiceResponseDto.PostItemServiceResponse;
import org.sopt.support.fixture.PostFixture;
import org.sopt.support.fixture.UserFixture;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;

@ExtendWith(MockitoExtension.class)
@DisplayName("게시물 아이디로 검색 서비스 테스트")
class GetPostByIdServiceImplTest {

  @InjectMocks
  GetPostByIdServiceImpl getPostByIdService;

  @Mock
  private UserReader userReader;
  @Mock
  private PostReader postReader;

  private User user;
  private User writer;
  private Post post;

  @BeforeEach
  void setUp() {
    user = UserFixture.create(1L);
    writer = UserFixture.create(2L);
    post = PostFixture.create(3L, writer);
  }

  @DisplayName("게시물 아이디로 검색을 성공")
  @Test
  void getPostById_Success() {
    // given
    given(userReader.getUserOrThrow(1L)).willReturn(user);
    given(postReader.getPostOrThrow(2L)).willReturn(post);

    // when
    final PostItemServiceResponse result = getPostByIdService.execute(1L, 2L);

    // then
    assertThat(result).isNotNull();
    assertThat(result.title()).isEqualTo(post.getTitle());
    assertThat(result.content()).isEqualTo(post.getContent());
    assertThat(result.writerName()).isEqualTo(writer.getName());
  }

  @DisplayName("유저 존재하지 않을 시 검색 실패")
  @Test
  void getPostById_WhenUserNotExist_Fail() {
    // given
    given(userReader.getUserOrThrow(1L)).willThrow(new ApiException(ErrorCode.NOT_FOUND_USER));

    // when & then
    assertThatThrownBy(() -> getPostByIdService.execute(1L, 2L))
        .isInstanceOf(ApiException.class)
        .hasMessage(ErrorCode.NOT_FOUND_USER.getMessage());
  }

  @DisplayName("게시물 존재하지 않을 시 검색 실패")
  @Test
  void getPostById_WhenPostNotExist_Fail() {
    // given
    given(userReader.getUserOrThrow(1L)).willReturn(user);
    given(postReader.getPostOrThrow(2L)).willThrow(new ApiException(ErrorCode.NOT_FOUND_POST));


    // when & then
    assertThatThrownBy(() -> getPostByIdService.execute(1L, 2L))
        .isInstanceOf(ApiException.class)
        .hasMessage(ErrorCode.NOT_FOUND_POST.getMessage());
  }
}
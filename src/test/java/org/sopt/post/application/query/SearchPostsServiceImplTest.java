package org.sopt.post.application.query;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sopt.post.application.reader.PostReader;
import org.sopt.post.domain.Post;
import org.sopt.support.fixture.PostFixture;
import org.sopt.support.fixture.UserFixture;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;


@ExtendWith(MockitoExtension.class)
@DisplayName("게시물 리스트 검색 서비스 테스트")
class SearchPostsServiceImplTest {

  @InjectMocks
  SearchPostsServiceImpl searchPostsService;

  @Mock
  private UserReader userReader;

  @Mock
  private PostReader postReader;

  private User user;
  private User writer;
  private Post post1;
  private Post post2;
  private Post post3;

  @BeforeEach
  void setUp() {
    user = UserFixture.create(1L);
    writer = UserFixture.create(2L);
    post1 = PostFixture.create(3L, writer);
    post2 = PostFixture.create(4L, writer);
    post3 = PostFixture.create(5L, writer);
  }

  @DisplayName("")
  @Test
  void test() {
    // given

    // when

    // then
  }
}
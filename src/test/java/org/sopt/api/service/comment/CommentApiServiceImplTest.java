package org.sopt.api.service.comment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sopt.api.service.comment.dto.CommentRequestDto;
import org.sopt.api.service.comment.dto.CommentRequestDto.CommentCreateRequestDto;
import org.sopt.api.service.comment.dto.CommentResponseDto.CommentItemDto;

@ExtendWith(MockitoExtension.class)
class CommentApiServiceImplTest {

  @InjectMocks
  CommentApiServiceImpl commentApiService;

  @Nested
  @DisplayName("댓글 생성 테스트")
  public class createCommentTest {

    @Test
    @DisplayName("댓글 생성에 성공한다.")
    public void createComment_Success() {
      // given
      final long userId = 1L;
      final String content = "content";
      final CommentCreateRequestDto request = CommentCreateRequestDto.builder()
          .userId(userId)
          .content(content)
          .build();

      // when
      final CommentItemDto result = commentApiService.createComment(request);

      // then
      assertThat(result).isNotNull();
      assertThat(result.content()).isEqualTo(content);
      assertThat(result.userDto().userId()).isEqualTo(userId);
    }
  }
}
package org.sopt.api.service.comment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.Builder;
import org.sopt.api.service.comment.dto.CommentResponseDto.CommentItemDto;
import org.sopt.api.service.comment.dto.CommentResponseDto.UserDto;

@JsonInclude(Include.NON_NULL)
public sealed interface CommentResponseDto permits CommentItemDto, UserDto {

  @Builder
  record CommentItemDto(
      Long commentId,
      UserDto userDto,
      String content,
      LocalDateTime createdAt,
      LocalDateTime updatedAt
  ) implements CommentResponseDto {

  }

  @Builder
  record UserDto(Long userId, String writerName) implements CommentResponseDto {

  }
}

package org.sopt.api.service.comment.dto;

import lombok.Builder;
import org.sopt.api.service.comment.dto.CommentRequestDto.CommentCreateRequestDto;

public sealed interface CommentRequestDto permits CommentCreateRequestDto {

  @Builder
  record CommentCreateRequestDto(
      Long userId,
      String content
  ) implements CommentRequestDto {

  }
}

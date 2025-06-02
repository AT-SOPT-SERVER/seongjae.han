package org.sopt.comment.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.comment.dto.CommentRequestDto.CommentCreateRequestDto;

public sealed interface CommentRequestDto permits CommentCreateRequestDto {

  @Builder(access = AccessLevel.PRIVATE)
  record CommentCreateRequestDto(
      Long userId,
      Long postId,
      String content
  ) implements CommentRequestDto {

    public static CommentCreateRequestDto of(
        Long userId,
        Long postId,
        String content
    ) {
      return CommentCreateRequestDto.builder()
          .userId(userId)
          .postId(postId)
          .content(content)
          .build();
    }
  }
}

package org.sopt.comment.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.comment.dto.CommentRequestDto.CommentCreateRequestDto;
import org.sopt.comment.dto.CommentRequestDto.CommentDeleteRequestDto;
import org.sopt.comment.dto.CommentRequestDto.CommentUpdateRequestDto;

public sealed interface CommentRequestDto permits CommentCreateRequestDto, CommentDeleteRequestDto,
    CommentUpdateRequestDto {

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

  @Builder(access = AccessLevel.PRIVATE)
  record CommentUpdateRequestDto(
      Long userId,
      Long commentId,
      String content
  ) implements CommentRequestDto {

    public static CommentUpdateRequestDto of(
        Long userId,
        Long commentId,
        String content
    ) {
      return CommentUpdateRequestDto.builder()
          .userId(userId)
          .commentId(commentId)
          .content(content)
          .build();
    }
  }

  @Builder(access = AccessLevel.PRIVATE)
  record CommentDeleteRequestDto(
      Long userId,
      Long commentId
  ) implements CommentRequestDto {

    public static CommentDeleteRequestDto of(
        Long userId,
        Long commentId
    ) {
      return CommentDeleteRequestDto.builder()
          .userId(userId)
          .commentId(commentId)
          .build();
    }
  }
}

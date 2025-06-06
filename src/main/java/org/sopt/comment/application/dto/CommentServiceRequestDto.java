package org.sopt.comment.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.comment.application.dto.CommentServiceRequestDto.CommentCreateServiceRequestDto;
import org.sopt.comment.application.dto.CommentServiceRequestDto.CommentDeleteServiceRequestDto;
import org.sopt.comment.application.dto.CommentServiceRequestDto.CommentListServiceRequestDto;
import org.sopt.comment.application.dto.CommentServiceRequestDto.CommentUpdateServiceRequestDto;

public sealed interface CommentServiceRequestDto permits CommentCreateServiceRequestDto,
    CommentDeleteServiceRequestDto, CommentListServiceRequestDto, CommentUpdateServiceRequestDto {

  @Builder(access = AccessLevel.PRIVATE)
  record CommentCreateServiceRequestDto(
      Long userId,
      Long postId,
      String content
  ) implements CommentServiceRequestDto {

    public static CommentCreateServiceRequestDto of(
        Long userId,
        Long postId,
        String content
    ) {
      return CommentCreateServiceRequestDto.builder()
          .userId(userId)
          .postId(postId)
          .content(content)
          .build();
    }
  }

  @Builder(access = AccessLevel.PRIVATE)
  record CommentUpdateServiceRequestDto(
      Long userId,
      Long commentId,
      String content
  ) implements CommentServiceRequestDto {

    public static CommentUpdateServiceRequestDto of(
        Long userId,
        Long commentId,
        String content
    ) {
      return CommentUpdateServiceRequestDto.builder()
          .userId(userId)
          .commentId(commentId)
          .content(content)
          .build();
    }
  }

  @Builder(access = AccessLevel.PRIVATE)
  record CommentDeleteServiceRequestDto(
      Long userId,
      Long commentId
  ) implements CommentServiceRequestDto {

    public static CommentDeleteServiceRequestDto of(
        Long userId,
        Long commentId
    ) {
      return CommentDeleteServiceRequestDto.builder()
          .userId(userId)
          .commentId(commentId)
          .build();
    }
  }

  @Builder(access = AccessLevel.PRIVATE)
  record CommentListServiceRequestDto(
      Long postId,
      int page,
      int size,
      String sortDirection
  ) implements CommentServiceRequestDto {

    public CommentListServiceRequestDto(Long postId, int page, int size) {
      this(postId, page, size, "desc");
    }

    public CommentListServiceRequestDto(Long postId) {
      this(postId, 0, 20, "desc");
    }
  }
}

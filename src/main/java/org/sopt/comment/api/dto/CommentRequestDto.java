package org.sopt.comment.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.comment.api.dto.CommentRequestDto.CommentCreateRequestDto;
import org.sopt.comment.api.dto.CommentRequestDto.CommentDeleteRequestDto;
import org.sopt.comment.api.dto.CommentRequestDto.CommentListRequestDto;
import org.sopt.comment.api.dto.CommentRequestDto.CommentUpdateRequestDto;
import org.sopt.comment.application.dto.CommentServiceRequestDto;
import org.sopt.comment.application.dto.CommentServiceRequestDto.CommentCreateServiceRequestDto;
import org.sopt.comment.application.dto.CommentServiceRequestDto.CommentDeleteServiceRequestDto;
import org.sopt.comment.application.dto.CommentServiceRequestDto.CommentListServiceRequestDto;
import org.sopt.comment.application.dto.CommentServiceRequestDto.CommentUpdateServiceRequestDto;

public sealed interface CommentRequestDto permits CommentCreateRequestDto,
    CommentDeleteRequestDto, CommentListRequestDto, CommentUpdateRequestDto {

  public CommentServiceRequestDto toServiceRequest();

  @Builder(access = AccessLevel.PRIVATE)
  record CommentCreateRequestDto(
      Long userId,
      Long postId,
      String content
  ) implements CommentRequestDto {

    @Override
    public CommentCreateServiceRequestDto toServiceRequest() {
      return CommentCreateServiceRequestDto.of(
          userId(), postId(), content()
      );
    }
  }

  @Builder(access = AccessLevel.PRIVATE)
  record CommentUpdateRequestDto(
      Long userId,
      Long commentId,
      String content
  ) implements CommentRequestDto {

    @Override
    public CommentUpdateServiceRequestDto toServiceRequest() {
      return CommentUpdateServiceRequestDto.of(userId(), commentId(), content());
    }
  }

  @Builder(access = AccessLevel.PRIVATE)
  record CommentDeleteRequestDto(
      Long userId,
      Long commentId
  ) implements CommentRequestDto {

    @Override
    public CommentDeleteServiceRequestDto toServiceRequest() {
      return CommentDeleteServiceRequestDto.of(userId(), commentId());
    }
  }

  @Builder(access = AccessLevel.PRIVATE)
  record CommentListRequestDto(
      Long postId,
      int page,
      int size,
      String sortDirection
  ) implements CommentRequestDto {

    @Override
    public CommentListServiceRequestDto toServiceRequest() {
      return CommentListServiceRequestDto.of(postId(), page(), size(), sortDirection());
    }
  }
}

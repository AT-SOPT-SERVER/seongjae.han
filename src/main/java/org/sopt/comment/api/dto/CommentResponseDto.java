package org.sopt.comment.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.comment.api.dto.CommentResponseDto.CommentItemDto;
import org.sopt.comment.api.dto.CommentResponseDto.CommentListDto;
import org.sopt.comment.api.dto.CommentResponseDto.UserDto;
import org.sopt.comment.application.dto.CommentServiceResponseDto;

@JsonInclude(Include.NON_NULL)
public sealed interface CommentResponseDto permits CommentListDto, CommentItemDto, UserDto {

  @Builder(access = AccessLevel.PROTECTED)
  record CommentItemDto(
      Long commentId,
      Long postId,
      UserDto userDto,
      String content,
      LocalDateTime createdAt,
      LocalDateTime updatedAt
  ) implements CommentResponseDto {

    public static CommentItemDto from(
        final CommentServiceResponseDto.CommentItemDto commentItemServiceDto) {
      return CommentItemDto.builder()
          .commentId(commentItemServiceDto.commentId())
          .postId(commentItemServiceDto.postId())
          .userDto(UserDto.builder()
              .userId(commentItemServiceDto.userDto().userId())
              .writerName(commentItemServiceDto.userDto().writerName())
              .build())
          .content(commentItemServiceDto.content())
          .createdAt(commentItemServiceDto.createdAt())
          .updatedAt(commentItemServiceDto.updatedAt())
          .build();
    }

  }

  @Builder
  record UserDto(Long userId, String writerName) implements CommentResponseDto {

  }

  @Builder(access = AccessLevel.PROTECTED)
  record CommentListDto(
      List<CommentItemDto> comments,
      long totalElements,
      int totalPages,
      int currentPage,
      int pageSize,
      boolean hasNext,
      boolean hasPrevious
  ) implements CommentResponseDto {

    public static CommentListDto from(
        final CommentServiceResponseDto.CommentListDto commentListServiceDto) {

      List<CommentItemDto> responses = commentListServiceDto.comments().stream()
          .map(CommentItemDto::from)
          .toList();

      return CommentListDto.builder()
          .comments(responses)
          .totalElements(commentListServiceDto.totalElements())
          .totalPages(commentListServiceDto.totalPages())
          .currentPage(commentListServiceDto.currentPage())
          .pageSize(commentListServiceDto.pageSize())
          .hasNext(commentListServiceDto.hasNext())
          .hasPrevious(commentListServiceDto.hasPrevious())
          .build();
    }
  }
}

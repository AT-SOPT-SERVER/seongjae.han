package org.sopt.comment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.dto.CommentResponseDto.CommentItemDto;
import org.sopt.comment.dto.CommentResponseDto.CommentListDto;
import org.sopt.comment.dto.CommentResponseDto.UserDto;

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

    public static CommentItemDto from(final Comment saved) {
      return CommentItemDto.builder()
          .commentId(saved.getId())
          .postId(saved.getPost().getId())
          .userDto(UserDto.builder()
              .userId(saved.getUser().getId())
              .writerName(saved.getUser().getName())
              .build())
          .content(saved.getContent())
          .createdAt(saved.getCreatedAt())
          .updatedAt(saved.getUpdatedAt())
          .build();
    }
  }

  @Builder
  record UserDto(Long userId, String writerName) implements CommentResponseDto {

  }

  @Builder
  record CommentListDto(
      List<CommentItemDto> comments,
      long totalElements,
      int totalPages,
      int currentPage,
      int pageSize,
      boolean hasNext,
      boolean hasPrevious
  ) implements CommentResponseDto {

  }
}

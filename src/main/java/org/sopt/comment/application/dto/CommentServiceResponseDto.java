package org.sopt.comment.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.application.dto.CommentServiceResponseDto.CommentItemDto;
import org.sopt.comment.application.dto.CommentServiceResponseDto.CommentListDto;
import org.sopt.comment.application.dto.CommentServiceResponseDto.UserDto;
import org.springframework.data.domain.Page;

@JsonInclude(Include.NON_NULL)
public sealed interface CommentServiceResponseDto permits CommentListDto, CommentItemDto, UserDto {

  @Builder(access = AccessLevel.PROTECTED)
  record CommentItemDto(
      Long commentId,
      Long postId,
      UserDto userDto,
      String content,
      LocalDateTime createdAt,
      LocalDateTime updatedAt
  ) implements CommentServiceResponseDto {

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
  record UserDto(Long userId, String writerName) implements CommentServiceResponseDto {

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
  ) implements CommentServiceResponseDto {

    public static CommentListDto from(final Page<Comment> commentPage) {
      final List<CommentItemDto> comments = commentPage.getContent().stream()
          .map(CommentItemDto::from)
          .toList();

      return CommentListDto.builder()
          .comments(comments)
          .totalElements(commentPage.getTotalElements())
          .totalPages(commentPage.getTotalPages())
          .currentPage(commentPage.getNumber())
          .pageSize(commentPage.getSize())
          .hasNext(commentPage.hasNext())
          .hasPrevious(commentPage.hasPrevious())
          .build();
    }
  }
}

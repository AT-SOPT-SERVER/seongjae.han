package org.sopt.post.api.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.comment.api.dto.CommentResponseDto.CommentListDto;
import org.sopt.post.api.dto.PostResponseDto.PostItemResponse;
import org.sopt.post.api.dto.PostResponseDto.PostListResponse;
import org.sopt.post.application.dto.PostServiceResponseDto.PostItemServiceResponse;
import org.sopt.post.application.dto.PostServiceResponseDto.PostListServiceResponse;

public sealed interface PostResponseDto permits PostItemResponse, PostListResponse {

  @Builder(access = AccessLevel.PROTECTED)
  record PostItemResponse(String title, String content, String writerName,
                          CommentListDto commentListDto) implements
      PostResponseDto {

    public static PostItemResponse from(PostItemServiceResponse postItemServiceResponse) {
      return PostItemResponse.builder()
          .title(postItemServiceResponse.title())
          .content(postItemServiceResponse.content())
          .writerName(postItemServiceResponse.writerName())
          .commentListDto(CommentListDto.from(postItemServiceResponse.commentListDto()))
          .build();
    }
  }

  @Builder(access = AccessLevel.PROTECTED)
  record PostListResponse(
      List<PostHeaderDto> postHeaders,
      long totalElements,
      int totalPages,
      int currentPage,
      int pageSize,
      boolean hasNext,
      boolean hasPrevious
  ) implements
      PostResponseDto {

    public static PostListResponse from(PostListServiceResponse postListServiceResponse) {
      final List<PostListServiceResponse.PostHeaderDto> postHeaderDtos = postListServiceResponse.postHeaders();

      return PostListResponse.builder().postHeaders(
          postHeaderDtos.stream()
              .map(PostHeaderDto::from)
              .toList())
          .totalElements(postListServiceResponse.totalElements())
          .totalPages(postListServiceResponse.totalPages())
          .currentPage(postListServiceResponse.currentPage())
          .pageSize(postListServiceResponse.pageSize())
          .hasNext(postListServiceResponse.hasNext())
          .hasPrevious(postListServiceResponse.hasPrevious())
          .build();
    }

    @Builder(access = AccessLevel.PROTECTED)
    public record PostHeaderDto(String title, String writerName) {

      public static PostHeaderDto from(PostListServiceResponse.PostHeaderDto postHeaderDto) {

        return PostHeaderDto.builder()
            .title(postHeaderDto.title())
            .writerName(postHeaderDto.writerName())
            .build();
      }
    }
  }
}

package org.sopt.post.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.comment.application.dto.CommentServiceResponseDto.CommentListDto;
import org.sopt.post.domain.Post;
import org.sopt.post.application.dto.PostServiceResponseDto.PostItemServiceResponse;
import org.sopt.post.application.dto.PostServiceResponseDto.PostListServiceResponse;
import org.springframework.data.domain.Page;

public sealed interface PostServiceResponseDto permits PostListServiceResponse,
    PostItemServiceResponse {

  @Builder(access = AccessLevel.PROTECTED)
  record PostListServiceResponse(
      List<PostHeaderDto> postHeaders,
      long totalElements,
      int totalPages,
      int currentPage,
      int pageSize,
      boolean hasNext,
      boolean hasPrevious
  ) implements
      PostServiceResponseDto {

    public static PostListServiceResponse from(final Page<Post> postPage) {
      final List<PostHeaderDto> postHeaders = postPage.getContent().stream()
          .map(PostHeaderDto::from)
          .toList();

      return PostListServiceResponse.builder()
          .postHeaders(postHeaders)
          .totalElements(postPage.getTotalElements())
          .totalPages(postPage.getTotalPages())
          .currentPage(postPage.getNumber())
          .pageSize(postPage.getSize())
          .hasNext(postPage.hasNext())
          .hasPrevious(postPage.hasPrevious())
          .build();
    }


    public static PostListServiceResponse from(final List<Post> posts) {

      return PostListServiceResponse.builder().postHeaders(
          posts.stream()
              .map(PostHeaderDto::from)
              .toList()
      ).build();
    }

    @Builder(access = AccessLevel.PROTECTED)
    public record PostHeaderDto(Long postId, String title, String writerName) {

      public static PostHeaderDto from(Post post) {

        return PostHeaderDto.builder()
            .postId(post.getId())
            .title(post.getTitle())
            .writerName(post.getUser().getName())
            .build();
      }
    }
  }

  @Builder(access = AccessLevel.PROTECTED)
  record PostItemServiceResponse(String title, String content, String writerName,
                                 CommentListDto commentListDto) implements PostServiceResponseDto {

    public static PostItemServiceResponse from(final Post post) {
      return PostItemServiceResponse.builder()
          .title(post.getTitle())
          .commentListDto(CommentListDto.from(post.getComments()))
          .content(post.getContent())
          .writerName(post.getUser().getName())
          .build();
    }
  }
}

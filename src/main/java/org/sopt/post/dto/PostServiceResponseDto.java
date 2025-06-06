package org.sopt.post.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.PostServiceResponseDto.PostItemServiceResponse;
import org.sopt.post.dto.PostServiceResponseDto.PostListServiceResponse;

public sealed interface PostServiceResponseDto permits PostListServiceResponse,
    PostItemServiceResponse {

  @Builder(access = AccessLevel.PROTECTED)
  record PostListServiceResponse(List<PostHeaderDto> postHeaders) implements
      PostServiceResponseDto {

    public static PostListServiceResponse from(final List<Post> posts) {

      return PostListServiceResponse.builder().postHeaders(
          posts.stream()
              .map(PostHeaderDto::from)
              .toList()
      ).build();
    }

    @Builder(access = AccessLevel.PROTECTED)
    public static record PostHeaderDto(String title, String writerName) {

      public static PostHeaderDto from(Post post) {

        return PostHeaderDto.builder()
            .title(post.getTitle())
            .writerName(post.getUser().getName())
            .build();
      }
    }
  }

  @Builder(access = AccessLevel.PROTECTED)
  record PostItemServiceResponse(String title, String content, String writerName) implements
      PostServiceResponseDto {

    public static PostItemServiceResponse from(final Post post) {
      return PostItemServiceResponse.builder()
          .title(post.getTitle())
          .content(post.getContent())
          .writerName(post.getUser().getName())
          .build();
    }
  }
}

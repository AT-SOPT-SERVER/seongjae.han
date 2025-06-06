package org.sopt.post.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.PostServiceResponseDto.PostListServiceResponse;
import org.sopt.post.dto.PostServiceResponseDto.PostItemServiceResponse;

public sealed interface PostServiceResponseDto permits PostListServiceResponse,
    PostItemServiceResponse {

  record PostListServiceResponse(List<PostHeaderDto> postHeaders) implements PostServiceResponseDto {

    public static record PostHeaderDto(String title, String writerName) {
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

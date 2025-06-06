package org.sopt.post.dto;

import java.util.List;
import org.sopt.post.dto.PostServiceResponseDto.PostListServiceResponse;
import org.sopt.post.dto.PostServiceResponseDto.PostItemServiceResponse;

public sealed interface PostServiceResponseDto permits PostListServiceResponse,
    PostItemServiceResponse {

  record PostListServiceResponse(List<PostHeaderDto> postHeaders) implements PostServiceResponseDto {

    public static record PostHeaderDto(String title, String writerName) {
    }
  }

  record PostItemServiceResponse(String title, String content, String writerName) implements
      PostServiceResponseDto {

  }
}

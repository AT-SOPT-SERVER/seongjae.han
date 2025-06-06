package org.sopt.post.dto;

import java.util.List;
import org.sopt.post.dto.PostServiceResponseDto.ListServiceResponse;
import org.sopt.post.dto.PostServiceResponseDto.itemServiceResponse;

public sealed interface PostServiceResponseDto permits ListServiceResponse, itemServiceResponse {

  record ListServiceResponse(List<PostHeaderDto> postHeaders) implements PostServiceResponseDto {

    public static record PostHeaderDto(String title, String writerName) {
    }
  }

  record itemServiceResponse(String title, String content, String writerName) implements
      PostServiceResponseDto {

  }
}

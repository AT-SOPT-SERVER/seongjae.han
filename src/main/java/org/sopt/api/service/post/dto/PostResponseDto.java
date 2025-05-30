package org.sopt.api.service.post.dto;

import java.util.List;
import org.sopt.api.service.post.dto.PostResponseDto.ListDto;
import org.sopt.api.service.post.dto.PostResponseDto.itemDto;

public sealed interface PostResponseDto permits ListDto, itemDto {

  record ListDto(List<PostHeaderDto> postHeaders) implements PostResponseDto {

    public static record PostHeaderDto(String title, String writerName) {
    }
  }

  record itemDto(String title, String content, String writerName) implements PostResponseDto {

  }
}

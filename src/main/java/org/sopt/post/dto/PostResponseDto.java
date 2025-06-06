package org.sopt.post.dto;

import java.util.List;
import org.sopt.post.dto.PostResponseDto.ListDto;
import org.sopt.post.dto.PostResponseDto.itemDto;

public sealed interface PostResponseDto permits ListDto, itemDto {

  record ListDto(List<PostHeaderDto> postHeaders) implements PostResponseDto {

    public static record PostHeaderDto(String title, String writerName) {
    }
  }

  record itemDto(String title, String content, String writerName) implements PostResponseDto {

  }
}

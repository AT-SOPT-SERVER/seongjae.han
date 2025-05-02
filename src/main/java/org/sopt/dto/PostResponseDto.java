package org.sopt.dto;

import java.util.List;
import org.sopt.dto.PostRequestDto.CreateRequest;
import org.sopt.dto.PostRequestDto.UpdateRequest;
import org.sopt.dto.PostResponseDto.ListDto;
import org.sopt.dto.PostResponseDto.itemDto;

public sealed interface PostResponseDto permits ListDto, itemDto {

  record ListDto(List<PostHeaderDto> postHeaders) implements PostResponseDto {

    public static record PostHeaderDto(String title, String writerName) {
    }
  }

  record itemDto(String title, String content, String writerName) implements PostResponseDto {

  }
}

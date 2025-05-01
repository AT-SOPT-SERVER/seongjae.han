package org.sopt.dto;

import java.util.List;
import org.sopt.dto.PostRequestDto.CreateRequest;
import org.sopt.dto.PostRequestDto.UpdateRequest;
import org.sopt.dto.PostResponseDto.ListDto;

public sealed interface PostResponseDto permits ListDto {

  record ListDto(List<PostHeaderDto> postHeaders) implements PostResponseDto {

    public static record PostHeaderDto(String title, String writerName) {
    }
  }
}

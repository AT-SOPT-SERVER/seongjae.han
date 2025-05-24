package org.sopt.dto;

import org.sopt.dto.PostRequestDto.CreateRequest;
import org.sopt.dto.PostRequestDto.UpdateRequest;

public sealed interface PostRequestDto permits CreateRequest, UpdateRequest {

  record CreateRequest(String title, String content) implements PostRequestDto {
  }

  record UpdateRequest(Long id, String title, String content) implements PostRequestDto {
  }
}

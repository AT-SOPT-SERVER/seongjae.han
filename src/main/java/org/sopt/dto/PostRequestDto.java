package org.sopt.dto;

import org.sopt.dto.PostRequestDto.CreateRequest;
import org.sopt.dto.PostRequestDto.UpdateRequest;

public sealed interface PostRequestDto permits CreateRequest, UpdateRequest {

  record CreateRequest(String title) implements PostRequestDto {
  }

  record UpdateRequest(Long id, String title) implements PostRequestDto {

  }
}

package org.sopt.api.service.post.dto;

import org.sopt.api.service.post.dto.PostRequestDto.CreateRequest;
import org.sopt.api.service.post.dto.PostRequestDto.UpdateRequest;

public sealed interface PostRequestDto permits CreateRequest, UpdateRequest {

  record CreateRequest(String title, String content) implements PostRequestDto {
  }

  record UpdateRequest(Long id, String title, String content) implements PostRequestDto {
  }
}

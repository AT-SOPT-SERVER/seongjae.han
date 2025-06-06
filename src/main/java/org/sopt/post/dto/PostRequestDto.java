package org.sopt.post.dto;

import org.sopt.post.dto.PostRequestDto.CreateRequest;
import org.sopt.post.dto.PostRequestDto.UpdateRequest;

public sealed interface PostRequestDto permits CreateRequest, UpdateRequest {

  record CreateRequest(String title, String content) implements PostRequestDto {
  }

  record UpdateRequest(Long id, String title, String content) implements PostRequestDto {
  }
}

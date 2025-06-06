package org.sopt.post.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.post.api.dto.PostRequestDto.CreatePostRequest;
import org.sopt.post.api.dto.PostRequestDto.UpdatePostRequest;
import org.sopt.post.application.dto.PostServiceRequestDto.CreatePostServiceRequest;
import org.sopt.post.application.dto.PostServiceRequestDto.UpdatePostServiceRequest;

public sealed interface PostRequestDto permits CreatePostRequest, UpdatePostRequest {

  @Builder(access = AccessLevel.PROTECTED)
  record CreatePostRequest(String title, String content) implements PostRequestDto {

    public CreatePostServiceRequest toServiceRequest() {

      return CreatePostServiceRequest.of(title(), content());
    }

  }

  @Builder(access = AccessLevel.PROTECTED)
  record UpdatePostRequest(Long postId, String title, String content) implements PostRequestDto {

    public UpdatePostServiceRequest toServiceRequest() {
      return UpdatePostServiceRequest.of(postId(), title(), content());
    }

  }
}

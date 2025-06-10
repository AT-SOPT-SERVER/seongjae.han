package org.sopt.like.api.dto;

import org.sopt.like.api.dto.LikeRequestDto.CreatePostLikeRequest;
import org.sopt.like.application.dto.LikeServiceRequestDto.CreatePostLikeServiceRequest;

public sealed interface LikeRequestDto permits CreatePostLikeRequest {

  record CreatePostLikeRequest(
      Long postId
  ) implements LikeRequestDto {

    public CreatePostLikeServiceRequest toServiceRequest() {
      return CreatePostLikeServiceRequest.of(postId);
    }
  }
}

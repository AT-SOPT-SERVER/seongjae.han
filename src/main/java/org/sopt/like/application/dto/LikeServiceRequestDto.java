package org.sopt.like.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.like.application.dto.LikeServiceRequestDto.CreatePostLikeServiceRequest;

public sealed interface LikeServiceRequestDto permits CreatePostLikeServiceRequest {

  @Builder(access = AccessLevel.PROTECTED)
  record CreatePostLikeServiceRequest(
      Long postId
  ) implements LikeServiceRequestDto {

    public static CreatePostLikeServiceRequest of(final Long postId) {
      return CreatePostLikeServiceRequest.builder().postId(postId).build();
    }
  }
}

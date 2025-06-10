package org.sopt.like.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.like.api.dto.LikeResponseDto.CreatePostLikeResponse;
import org.sopt.like.application.dto.LikeServiceResponseDto.CreatePostLikeServiceResponse;
import org.sopt.like.domain.Like;

public sealed interface LikeServiceResponseDto permits CreatePostLikeServiceResponse {

  @Builder(access = AccessLevel.PROTECTED)
  record CreatePostLikeServiceResponse(
      Long likeId,
      Long userId,
      Long postId
  ) implements LikeServiceResponseDto {

    public static CreatePostLikeServiceResponse from(final Like saved) {
      return CreatePostLikeServiceResponse.builder()
          .likeId(saved.getId())
          .userId(saved.getUser().getId())
          .postId(saved.getTargetId())
          .build();
    }
  }

}

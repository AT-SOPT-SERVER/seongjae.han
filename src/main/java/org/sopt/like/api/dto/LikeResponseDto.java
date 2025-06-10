package org.sopt.like.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.like.api.dto.LikeResponseDto.CreateCommentLikeResponse;
import org.sopt.like.api.dto.LikeResponseDto.CreatePostLikeResponse;
import org.sopt.like.application.dto.LikeServiceResponseDto.CreateCommentLikeServiceResponse;
import org.sopt.like.application.dto.LikeServiceResponseDto.CreatePostLikeServiceResponse;
import org.sopt.like.domain.Like;

public sealed interface LikeResponseDto permits CreateCommentLikeResponse, CreatePostLikeResponse {

  @Builder(access = AccessLevel.PROTECTED)
  record CreatePostLikeResponse(
      Long likeId,
      Long userId,
      Long postId
  ) implements LikeResponseDto {

    public static CreatePostLikeResponse from(final CreatePostLikeServiceResponse serviceResponse) {
      return CreatePostLikeResponse.builder()
          .likeId(serviceResponse.likeId())
          .userId(serviceResponse.userId())
          .postId(serviceResponse.postId())
          .build();
    }
  }

  @Builder(access = AccessLevel.PROTECTED)
  record CreateCommentLikeResponse(
      Long likeId,
      Long userId,
      Long commentId
  ) implements LikeResponseDto {

    public static CreateCommentLikeResponse from(final CreateCommentLikeServiceResponse serviceResponse) {
      return CreateCommentLikeResponse.builder()
          .likeId(serviceResponse.likeId())
          .userId(serviceResponse.userId())
          .commentId(serviceResponse.commentId())
          .build();
    }
  }

}

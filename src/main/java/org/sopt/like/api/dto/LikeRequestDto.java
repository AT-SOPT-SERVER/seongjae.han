package org.sopt.like.api.dto;

import org.sopt.like.api.dto.LikeRequestDto.CreateCommentLikeRequest;
import org.sopt.like.api.dto.LikeRequestDto.CreatePostLikeRequest;
import org.sopt.like.application.dto.LikeServiceRequestDto.CreateCommentLikeServiceRequest;
import org.sopt.like.application.dto.LikeServiceRequestDto.CreatePostLikeServiceRequest;

public sealed interface LikeRequestDto permits CreateCommentLikeRequest, CreatePostLikeRequest {

  record CreatePostLikeRequest(
      Long postId
  ) implements LikeRequestDto {

    public CreatePostLikeServiceRequest toServiceRequest() {
      return CreatePostLikeServiceRequest.of(postId());
    }
  }

  record CreateCommentLikeRequest(
      Long commentId
  ) implements LikeRequestDto {

    public CreateCommentLikeServiceRequest toServiceRequest() {
      return CreateCommentLikeServiceRequest.of(commentId());
    }
  }
}

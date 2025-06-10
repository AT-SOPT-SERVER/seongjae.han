package org.sopt.like.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.like.application.dto.LikeServiceRequestDto.CreateCommentLikeServiceRequest;
import org.sopt.like.application.dto.LikeServiceRequestDto.CreatePostLikeServiceRequest;

public sealed interface LikeServiceRequestDto permits CreateCommentLikeServiceRequest,
    CreatePostLikeServiceRequest {

  @Builder(access = AccessLevel.PROTECTED)
  record CreatePostLikeServiceRequest(
      Long postId
  ) implements LikeServiceRequestDto {

    public static CreatePostLikeServiceRequest of(final Long postId) {
      return CreatePostLikeServiceRequest.builder().postId(postId).build();
    }
  }

  @Builder(access = AccessLevel.PROTECTED)
  record CreateCommentLikeServiceRequest(
      Long commentId
  ) implements LikeServiceRequestDto {

    public static CreateCommentLikeServiceRequest of(final Long commentId) {
      return CreateCommentLikeServiceRequest.builder().commentId(commentId).build();
    }
  }
}

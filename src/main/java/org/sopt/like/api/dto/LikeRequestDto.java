package org.sopt.like.api.dto;

import org.sopt.like.api.dto.LikeRequestDto.CreateCommentLikeRequest;
import org.sopt.like.api.dto.LikeRequestDto.CreatePostLikeRequest;
import org.sopt.like.api.dto.LikeRequestDto.DeletePostLikeRequest;
import org.sopt.like.application.dto.LikeServiceRequestDto.CreateCommentLikeServiceRequest;
import org.sopt.like.application.dto.LikeServiceRequestDto.CreatePostLikeServiceRequest;
import org.sopt.like.application.dto.LikeServiceRequestDto.DeleteCommentLikeServiceRequest;
import org.sopt.like.application.dto.LikeServiceRequestDto.DeletePostLikeServiceRequest;

public sealed interface LikeRequestDto permits CreateCommentLikeRequest, CreatePostLikeRequest,
    DeletePostLikeRequest {

  record CreatePostLikeRequest(
      Long postId
  ) implements LikeRequestDto {

    public CreatePostLikeServiceRequest toServiceRequest() {
      return CreatePostLikeServiceRequest.of(postId());
    }
  }

  record DeletePostLikeRequest(
      Long postId
  ) implements LikeRequestDto {

    public DeletePostLikeServiceRequest toServiceRequest() {
      return DeletePostLikeServiceRequest.of(postId());
    }
  }

  record CreateCommentLikeRequest(
      Long commentId
  ) implements LikeRequestDto {

    public CreateCommentLikeServiceRequest toServiceRequest() {
      return CreateCommentLikeServiceRequest.of(commentId());
    }
  }

  record DeleteCommentLikeRequest(
      Long commentId
  ) {
    public DeleteCommentLikeServiceRequest toServiceRequest() {
      return DeleteCommentLikeServiceRequest.of(commentId());
    }

  }
}

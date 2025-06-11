package org.sopt.like.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.like.api.dto.LikeResponseDto.CreateCommentLikeResponse;
import org.sopt.like.api.dto.LikeResponseDto.CreatePostLikeResponse;
import org.sopt.like.api.dto.LikeResponseDto.DeleteCommentLikeResponse;
import org.sopt.like.api.dto.LikeResponseDto.DeletePostLikeResponse;
import org.sopt.like.application.dto.LikeServiceResponseDto.CreateCommentLikeServiceResponse;
import org.sopt.like.application.dto.LikeServiceResponseDto.CreatePostLikeServiceResponse;
import org.sopt.like.application.dto.LikeServiceResponseDto.DeleteCommentLikeServiceResponse;
import org.sopt.like.application.dto.LikeServiceResponseDto.DeletePostLikeServiceResponse;

public sealed interface LikeResponseDto permits CreateCommentLikeResponse, CreatePostLikeResponse,
    DeleteCommentLikeResponse, DeletePostLikeResponse {

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
  record DeletePostLikeResponse(
      Long likeId,
      Long userId,
      Long postId
  ) implements LikeResponseDto {

    public static DeletePostLikeResponse from(final DeletePostLikeServiceResponse serviceResponse) {
      return DeletePostLikeResponse.builder()
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

  @Builder(access = AccessLevel.PROTECTED)
  record DeleteCommentLikeResponse(
      Long likeId,
      Long userId,
      Long commentId
  ) implements LikeResponseDto {

    public static DeleteCommentLikeResponse from(final DeleteCommentLikeServiceResponse serviceResponse) {
      return DeleteCommentLikeResponse.builder()
          .likeId(serviceResponse.likeId())
          .userId(serviceResponse.userId())
          .commentId(serviceResponse.commentId())
          .build();
    }
  }

}

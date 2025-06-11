package org.sopt.like.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.like.application.dto.LikeServiceResponseDto.CreateCommentLikeServiceResponse;
import org.sopt.like.application.dto.LikeServiceResponseDto.CreatePostLikeServiceResponse;
import org.sopt.like.application.dto.LikeServiceResponseDto.DeleteCommentLikeServiceResponse;
import org.sopt.like.application.dto.LikeServiceResponseDto.DeletePostLikeServiceResponse;
import org.sopt.like.domain.Like;

public sealed interface LikeServiceResponseDto permits CreateCommentLikeServiceResponse,
    CreatePostLikeServiceResponse, DeleteCommentLikeServiceResponse, DeletePostLikeServiceResponse {

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

  @Builder(access = AccessLevel.PROTECTED)
  record DeletePostLikeServiceResponse(
      Long likeId,
      Long userId,
      Long postId
  ) implements LikeServiceResponseDto {

    public static DeletePostLikeServiceResponse from(final Like saved) {
      return DeletePostLikeServiceResponse.builder()
          .likeId(saved.getId())
          .userId(saved.getUser().getId())
          .postId(saved.getTargetId())
          .build();
    }
  }


  @Builder(access = AccessLevel.PROTECTED)
  record CreateCommentLikeServiceResponse(
      Long likeId,
      Long userId,
      Long commentId
  ) implements LikeServiceResponseDto {

    public static CreateCommentLikeServiceResponse from(final Like saved) {
      return CreateCommentLikeServiceResponse.builder()
          .likeId(saved.getId())
          .userId(saved.getUser().getId())
          .commentId(saved.getTargetId())
          .build();
    }
  }

  @Builder(access = AccessLevel.PROTECTED)
  record DeleteCommentLikeServiceResponse(
      Long likeId,
      Long userId,
      Long commentId
  ) implements LikeServiceResponseDto {

    public static DeleteCommentLikeServiceResponse from(final Like saved) {
      return DeleteCommentLikeServiceResponse.builder()
          .likeId(saved.getId())
          .userId(saved.getUser().getId())
          .commentId(saved.getTargetId())
          .build();
    }
  }

}

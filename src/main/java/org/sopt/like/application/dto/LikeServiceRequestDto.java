package org.sopt.like.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.like.application.dto.LikeServiceRequestDto.CreateCommentLikeServiceRequest;
import org.sopt.like.application.dto.LikeServiceRequestDto.CreatePostLikeServiceRequest;
import org.sopt.like.application.dto.LikeServiceRequestDto.DeleteCommentLikeServiceRequest;
import org.sopt.like.application.dto.LikeServiceRequestDto.DeletePostLikeServiceRequest;

public sealed interface LikeServiceRequestDto permits CreateCommentLikeServiceRequest,
    CreatePostLikeServiceRequest, DeleteCommentLikeServiceRequest, DeletePostLikeServiceRequest {

  @Builder(access = AccessLevel.PROTECTED)
  record CreatePostLikeServiceRequest(
      Long postId
  ) implements LikeServiceRequestDto {

    public static CreatePostLikeServiceRequest of(final Long postId) {
      return CreatePostLikeServiceRequest.builder().postId(postId).build();
    }
  }

  @Builder(access = AccessLevel.PROTECTED)
  record DeletePostLikeServiceRequest(
      Long postId
  ) implements LikeServiceRequestDto {

    public static DeletePostLikeServiceRequest of(final Long postId) {
      return DeletePostLikeServiceRequest.builder().postId(postId).build();
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

//
@Builder(access = AccessLevel.PROTECTED)
record DeleteCommentLikeServiceRequest(
    Long commentId
) implements LikeServiceRequestDto {

  public static DeleteCommentLikeServiceRequest of(final Long commentId) {
    return DeleteCommentLikeServiceRequest.builder().commentId(commentId).build();
  }
}

}

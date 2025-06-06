package org.sopt.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.post.dto.PostServiceRequestDto.CreatePostServiceRequest;
import org.sopt.post.dto.PostServiceRequestDto.UpdatePostServiceRequest;

public sealed interface PostServiceRequestDto permits CreatePostServiceRequest,
    UpdatePostServiceRequest {

  @Builder(access = AccessLevel.PROTECTED)
  record CreatePostServiceRequest(String title, String content) implements PostServiceRequestDto {

    public static CreatePostServiceRequest of(String title, String content) {

      return CreatePostServiceRequest.builder()
          .title(title)
          .content(content)
          .build();
    }
  }

  @Builder(access = AccessLevel.PROTECTED)
  record UpdatePostServiceRequest(Long postId, String title, String content) implements
      PostServiceRequestDto {

    public static UpdatePostServiceRequest of(Long postId, String title, String content) {

      return UpdatePostServiceRequest.builder()
          .postId(postId)
          .title(title)
          .content(content)
          .build();
    }

  }
}

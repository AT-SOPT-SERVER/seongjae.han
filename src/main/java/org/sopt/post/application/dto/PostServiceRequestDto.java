package org.sopt.post.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.post.application.dto.PostServiceRequestDto.CreatePostServiceRequest;
import org.sopt.post.application.dto.PostServiceRequestDto.SearchPostListServiceRequest;
import org.sopt.post.application.dto.PostServiceRequestDto.UpdatePostServiceRequest;
import org.sopt.post.application.query.PostSearchSort;

public sealed interface PostServiceRequestDto permits CreatePostServiceRequest,
    SearchPostListServiceRequest, UpdatePostServiceRequest {

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
  record UpdatePostServiceRequest(long postId, String title, String content) implements
      PostServiceRequestDto {

    public static UpdatePostServiceRequest of(Long postId, String title, String content) {

      return UpdatePostServiceRequest.builder()
          .postId(postId)
          .title(title)
          .content(content)
          .build();
    }

  }

  @Builder(access = AccessLevel.PROTECTED)
  record SearchPostListServiceRequest(String keyword, PostSearchSort searchSort) implements
      PostServiceRequestDto {

    public static SearchPostListServiceRequest of(String keyword, PostSearchSort searchSort) {

      return SearchPostListServiceRequest.builder()
          .keyword(keyword)
          .searchSort(searchSort)
          .build();
    }

  }
}

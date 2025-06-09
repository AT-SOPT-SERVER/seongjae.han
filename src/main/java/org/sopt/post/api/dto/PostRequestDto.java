package org.sopt.post.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.post.api.dto.PostRequestDto.CreatePostRequest;
import org.sopt.post.api.dto.PostRequestDto.SearchPostListRequest;
import org.sopt.post.api.dto.PostRequestDto.UpdatePostRequest;
import org.sopt.post.application.dto.PostServiceRequestDto.CreatePostServiceRequest;
import org.sopt.post.application.dto.PostServiceRequestDto.SearchPostListServiceRequest;
import org.sopt.post.application.dto.PostServiceRequestDto.UpdatePostServiceRequest;
import org.sopt.post.application.query.PostSearchSort;

public sealed interface PostRequestDto permits CreatePostRequest, SearchPostListRequest,
    UpdatePostRequest {

  @Builder(access = AccessLevel.PROTECTED)
  record CreatePostRequest(String title, String content) implements PostRequestDto {

    public CreatePostServiceRequest toServiceRequest() {

      return CreatePostServiceRequest.of(title(), content());
    }

  }

  @Builder(access = AccessLevel.PROTECTED)
  record UpdatePostRequest(Long postId, String title, String content) implements PostRequestDto {

    public UpdatePostServiceRequest toServiceRequest() {
      return UpdatePostServiceRequest.of(postId(), title(), content());
    }

  }

  @Builder(access = AccessLevel.PROTECTED)
  record SearchPostListRequest(String keyword, PostSearchSort searchSort) implements PostRequestDto {

    public SearchPostListServiceRequest toServiceRequest() {
      return SearchPostListServiceRequest.of(keyword(), searchSort());
    }
  }
}

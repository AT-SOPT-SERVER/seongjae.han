package org.sopt.post.api.dto;

import jakarta.validation.constraints.NotNull;
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
  record CreatePostRequest(@NotNull(message = "게시물 제목은 필수값입니다.") String title,
                           @NotNull(message = "게시물 내용은 필수값입니다.") String content) implements
      PostRequestDto {

    public CreatePostServiceRequest toServiceRequest() {

      return CreatePostServiceRequest.of(title(), content());
    }

  }

  @Builder(access = AccessLevel.PROTECTED)
  record UpdatePostRequest(@NotNull(message = "게시물 아이디는 필수값입니다.") Long postId,
                           @NotNull(message = "게시물 제목은 필수값입니다.") String title,
                           @NotNull(message = "게시물 내용은 필수값입니다.") String content) implements
      PostRequestDto {

    public UpdatePostServiceRequest toServiceRequest() {
      return UpdatePostServiceRequest.of(postId(), title(), content());
    }

  }

  @Builder(access = AccessLevel.PROTECTED)
  record SearchPostListRequest(@NotNull(message = "키워드는 필수값입니다.") String keyword,
                               @NotNull(message = "검색 종류는 필수값입니다.") PostSearchSort searchSort) implements
      PostRequestDto {

    public SearchPostListServiceRequest toServiceRequest() {
      return SearchPostListServiceRequest.of(keyword(), searchSort());
    }
  }
}

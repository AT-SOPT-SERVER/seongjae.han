package org.sopt.post.application.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.global.util.PaginationUtils;
import org.sopt.post.application.dto.PostServiceRequestDto.CreatePostServiceRequest;
import org.sopt.post.application.dto.PostServiceRequestDto.GetAllPostsServiceRequest;
import org.sopt.post.application.dto.PostServiceRequestDto.SearchPostListServiceRequest;
import org.sopt.post.application.dto.PostServiceRequestDto.UpdatePostServiceRequest;
import org.sopt.post.application.query.PostSearchSort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public sealed interface PostServiceRequestDto permits CreatePostServiceRequest,
    GetAllPostsServiceRequest, SearchPostListServiceRequest, UpdatePostServiceRequest {

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
  record SearchPostListServiceRequest(
      int page,
      int size,
      String sortDirection,
      String keyword,
      PostSearchSort searchSort
  ) implements
      PostServiceRequestDto {

    public static SearchPostListServiceRequest of(Integer page, Integer size, String sortDirection,
        String keyword, PostSearchSort searchSort) {

      int safePage = PaginationUtils.correctPage(page);
      int safeSize = PaginationUtils.correctSize(size);
      String safeDirection = ("desc".equalsIgnoreCase(sortDirection)) ? "desc" : "asc";

      return SearchPostListServiceRequest.builder()
          .page(safePage)
          .size(safeSize)
          .sortDirection(safeDirection)
          .keyword(keyword)
          .searchSort(searchSort)
          .build();
    }

    public Pageable toPageable() {
      Direction direction = "asc".equalsIgnoreCase(sortDirection())
          ? Direction.ASC : Direction.DESC;
      Sort sort = Sort.by(direction, "createdAt");

      return PageRequest.of(page(), size(), sort);
    }
  }

  @Builder(access = AccessLevel.PROTECTED)
  record GetAllPostsServiceRequest(
      int page,
      int size,
      String sortDirection
  ) implements PostServiceRequestDto {

    public static GetAllPostsServiceRequest of(Integer page, Integer size, String sortDirection) {

      int safePage = PaginationUtils.correctPage(page);
      int safeSize = PaginationUtils.correctSize(size);
      String safeDirection = ("desc".equalsIgnoreCase(sortDirection)) ? "desc" : "asc";

      return GetAllPostsServiceRequest.builder()
          .page(safePage)
          .size(safeSize)
          .sortDirection(safeDirection)
          .build();
    }

    public Pageable toPageable() {
      Direction direction = "asc".equalsIgnoreCase(sortDirection())
          ? Direction.ASC : Direction.DESC;
      Sort sort = Sort.by(direction, "createdAt");

      return PageRequest.of(page(), size(), sort);
    }
  }
}

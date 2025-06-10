package org.sopt.post.application.dto;

import static org.sopt.global.constants.AppConstants.DEFAULT_PAGE_SIZE;

import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.global.constants.AppConstants;
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
  record SearchPostListServiceRequest(String keyword, PostSearchSort searchSort) implements
      PostServiceRequestDto {

    public static SearchPostListServiceRequest of(String keyword, PostSearchSort searchSort) {

      return SearchPostListServiceRequest.builder()
          .keyword(keyword)
          .searchSort(searchSort)
          .build();
    }
  }

  @Builder(access = AccessLevel.PROTECTED)
  record GetAllPostsServiceRequest(
      int page,
      int size,
      String sortDirection
  ) implements PostServiceRequestDto {

    public static GetAllPostsServiceRequest of(Integer page, Integer size, String sortDirection) {

      int safePage = page != null ? page : 0;
      int safeSize = size != null ? size : DEFAULT_PAGE_SIZE;
      String safeSortDirection = sortDirection != null ? sortDirection : "asc";

      return GetAllPostsServiceRequest.builder()
          .page(safePage)
          .size(safeSize)
          .sortDirection(safeSortDirection)
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

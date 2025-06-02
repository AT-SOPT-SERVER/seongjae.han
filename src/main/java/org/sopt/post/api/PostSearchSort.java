package org.sopt.post.api;

import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;

public enum PostSearchSort {

  POST_TITLE("게시물 제목"),
  POST_TAG("포스트 태그"),
  WRITER_NAME("게시물 작성자 명");

  private final String description;

  PostSearchSort(
      final String description
  ) {
    this.description = description;
  }

  public static PostSearchSort from(final String searchSort) {
    try {
      return PostSearchSort.valueOf(searchSort);
    } catch (IllegalArgumentException e) {
      throw new ApiException(ErrorCode.ILLEGAL_POST_SEARCH_SORT);
    }
  }


  public String getDescription() {
    return description;
  }
}

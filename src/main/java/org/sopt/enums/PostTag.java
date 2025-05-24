package org.sopt.enums;

import org.sopt.exceptions.ApiException;
import org.sopt.exceptions.ErrorCode;

public enum PostTag {

  BACKEND("백엔드"),
  DATABASE("데이터베이스"),
  INFRA("인프라");

  private final String description;

  PostTag(final String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public static PostTag from(final String keyword) {
    try {
      return PostTag.valueOf(keyword);
    } catch (IllegalArgumentException e) {
      throw new ApiException(ErrorCode.ILLEGAL_POST_TAG);
    }
  }
}

package org.sopt.enums;

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
}

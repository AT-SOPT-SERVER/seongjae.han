package org.sopt.enums;

public enum PostSearchSort {

  POST_TITLE("게시물 제목"),
  POST_TAG("포스트 태그"),
  WRITER_NAME("게시물 작성자 명");

  private final String description;

  PostSearchSort(final String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}

package org.sopt.enums;

public enum PostSearchSort {

  POST_TITLE("title", "게시물 제목"),
  POST_TAG("tag", "포스트 태그"),
  WRITER_NAME("writerName", "게시물 작성자 명");

  private final String code;
  private final String description;

  PostSearchSort(
      final String code,
      final String description
  ) {
    this.code = code;
    this.description = description;
  }

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }
}

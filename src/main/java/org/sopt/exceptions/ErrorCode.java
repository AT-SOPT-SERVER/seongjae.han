package org.sopt.exceptions;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

  // 400번대 (Client Error)
  BLANK_POST_TITLE(HttpStatus.BAD_REQUEST, "c4001", "제목이 비어있습니다."),
  TOO_LONG_POST_TITLE(HttpStatus.BAD_REQUEST, "c4002", "제목은 30글자를 넘을 수 없습니다."),
  DUPLICATE_POST_TITLE(HttpStatus.CONFLICT, "c4090", "이미 존재하는 게시글 제목입니다."),
  NOT_FOUND_POST(HttpStatus.NOT_FOUND, "c4041", "게시글을 찾을 수 없습니다."),
  TOO_MANY_POST_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, "c4290", "게시글은 3분 간격으로만 작성할 수 있습니다."),

  // 500번대 (Server Error)
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S5000","알 수 없는 서버 오류가 발생했습니다.");

  private final HttpStatus status;
  private final String code;
  private final String message;

  ErrorCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}

package org.sopt.comment.domain;

import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;

public class CommentValidator {

  public static void validateCommentLength(String content) {
    if (content == null || content.length() > 300) {
      throw new ApiException(ErrorCode.TOO_LONG_COMMENT_CONTENT);
    }
  }
}

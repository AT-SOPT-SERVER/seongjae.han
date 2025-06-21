package org.sopt.post.domain;

import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.global.util.GraphemeUtil;

public class PostValidator {

  private static final int POST_CONTENT_MAX_LENGTH = 1000;
  private static final int POST_TITLE_MAX_LENGTH = 30;

  public static void throwIfFieldsBlank(final String title, final String content) {
    if (title == null || title.isBlank()) {
      throw new ApiException(ErrorCode.BLANK_POST_TITLE);
    }

    if (content == null || content.isBlank()) {
      throw new ApiException(ErrorCode.BLANK_POST_CONTENT);
    }
  }

  public static void throwIfTitleLengthLong(final String title) {
    int count = GraphemeUtil.count(title);
    if (count > POST_TITLE_MAX_LENGTH) {
      throw new ApiException(ErrorCode.TOO_LONG_POST_TITLE);
    }
  }

  public static void throwIfContentLengthLong(final String content) {
    int count = GraphemeUtil.count(content);
    if (count > POST_CONTENT_MAX_LENGTH) {
      throw new ApiException(ErrorCode.TOO_LONG_POST_CONTENT);
    }
  }
}

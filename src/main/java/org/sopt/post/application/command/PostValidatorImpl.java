package org.sopt.post.application.command;

import lombok.RequiredArgsConstructor;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.post.application.reader.PostReader;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostValidatorImpl implements PostValidator {

  private final PostReader postReader;

  @Override
  public void validateDuplicateTitle(final String title) {
    if (postReader.existsByTitle(title)) {
      throw new ApiException(ErrorCode.DUPLICATE_POST_TITLE);
    }

  }
}

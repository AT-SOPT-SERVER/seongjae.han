package org.sopt.user.application.reader;

import lombok.RequiredArgsConstructor;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.user.domain.User;
import org.sopt.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader {

  private final UserRepository userRepository;

  @Override
  public User getUserOrThrow(final Long userId) {

    return userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
  }
}

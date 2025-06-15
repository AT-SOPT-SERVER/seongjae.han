package org.sopt.user.application.reader;

import org.sopt.user.domain.User;

public interface UserReader {

  User getUserOrThrow(Long userId);

  User getUserByLoginIdOrThrow(String loginId);
}

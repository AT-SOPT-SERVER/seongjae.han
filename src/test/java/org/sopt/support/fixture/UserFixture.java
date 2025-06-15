package org.sopt.support.fixture;

import org.sopt.user.domain.User;
import org.springframework.test.util.ReflectionTestUtils;

public class UserFixture {

  public static User create(Long id) {
    User user = User.createUser("loginId", "password", "nickname", "email@example.com");
    ReflectionTestUtils.setField(user, "id", id);

    return user;
  }
}

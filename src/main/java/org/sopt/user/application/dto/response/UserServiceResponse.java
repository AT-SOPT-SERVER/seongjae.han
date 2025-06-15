package org.sopt.user.application.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.user.application.dto.response.UserServiceResponse.SignupServiceResponse;
import org.sopt.user.domain.User;

public sealed interface UserServiceResponse permits SignupServiceResponse {

  @Builder(access = AccessLevel.PROTECTED)
  record SignupServiceResponse(Long id, String userName, String loginId) implements
      UserServiceResponse {

    public static SignupServiceResponse from(User user) {
      return SignupServiceResponse.builder()
          .id(user.getId())
          .loginId(user.getLoginId())
          .userName(user.getName())
          .build();
    }

  }
}

package org.sopt.user.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.user.api.dto.UserResponse.SignupResponse;
import org.sopt.user.application.dto.response.UserServiceResponse.SignupServiceResponse;

public sealed interface UserResponse permits SignupResponse {

  @Builder(access = AccessLevel.PRIVATE)
  record SignupResponse(Long id, String userName, String loginId) implements UserResponse {

    public static SignupResponse from(SignupServiceResponse serviceResponse) {
      return SignupResponse.builder()
          .id(serviceResponse.id())
          .userName(serviceResponse.userName())
          .loginId(serviceResponse.loginId())
          .build();
    }
  }
}

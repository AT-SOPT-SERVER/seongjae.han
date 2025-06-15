package org.sopt.user.application.dto.request;


import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.user.application.dto.request.UserServiceRequest.CreateServiceRequest;

public sealed interface UserServiceRequest permits CreateServiceRequest {

  @Builder(access = AccessLevel.PROTECTED)
  record CreateServiceRequest(String loginId, String password, String name, String email) implements
      UserServiceRequest {

    public static CreateServiceRequest of(String loginId, String password, String name,
        String email) {
      return CreateServiceRequest.builder()
          .loginId(loginId)
          .password(password)
          .name(name)
          .email(email)
          .build();
    }
  }
}

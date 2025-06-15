package org.sopt.user.api.dto;

import jakarta.validation.constraints.NotNull;
import org.sopt.user.api.dto.UserRequest.UserCreateRequest;
import org.sopt.user.application.dto.request.UserServiceRequest.CreateServiceRequest;

public sealed interface UserRequest permits UserCreateRequest {

  record UserCreateRequest(@NotNull(message = "로그인 아이디 값은 필수값입니다.") String loginId,
                           @NotNull(message = "비밀번호는 필수값입니다.")String password,
                           @NotNull(message = "유저 이름은 필수값입니다.")String name,
                           @NotNull(message = "유저 이메일은 필수값입니다.")String email
  ) implements
      UserRequest {

    public CreateServiceRequest toServiceRequest() {
      return CreateServiceRequest.of(loginId(), password(), name(), email());
    }
  }
}

package org.sopt.auth.api.dto;

import org.sopt.auth.application.dto.LoginServiceRequest;

public record LoginRequest(
    String loginId,
    String password
) {

  public LoginServiceRequest toServiceRequest() {
    return LoginServiceRequest.of(loginId(), password());
  }

}

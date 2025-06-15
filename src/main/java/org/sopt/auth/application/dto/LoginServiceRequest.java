package org.sopt.auth.application.dto;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PROTECTED)
public record LoginServiceRequest(
    String loginId,
    String password
) {

  public static LoginServiceRequest of(final String loginId, final String password) {
    return LoginServiceRequest.builder()
        .loginId(loginId)
        .password(password)
        .build();
  }
}

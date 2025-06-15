package org.sopt.auth.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.auth.application.dto.LoginServiceResponse;

@Builder(access = AccessLevel.PROTECTED)
public record LoginResponse(Long id, String loginId, String jwtToken) {

  public static LoginResponse from(final LoginServiceResponse serviceResponse) {

    return LoginResponse.builder()
        .id(serviceResponse.id())
        .loginId(serviceResponse.loginId())
        .jwtToken(serviceResponse.jwtToken())
        .build();
  }
}

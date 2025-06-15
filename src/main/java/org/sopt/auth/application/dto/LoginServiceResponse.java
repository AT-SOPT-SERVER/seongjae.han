package org.sopt.auth.application.dto;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PROTECTED)
public record LoginServiceResponse(Long id, String loginId, String jwtToken) {

  public static LoginServiceResponse of(Long id, String loginId, String jwtToken) {
    return LoginServiceResponse.builder()
        .id(id)
        .loginId(loginId)
        .jwtToken(jwtToken)
        .build();
  }
}

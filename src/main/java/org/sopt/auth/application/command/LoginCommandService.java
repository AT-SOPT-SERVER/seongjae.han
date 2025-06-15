package org.sopt.auth.application.command;


import lombok.RequiredArgsConstructor;
import org.sopt.auth.application.dto.LoginServiceRequest;
import org.sopt.auth.application.dto.LoginServiceResponse;
import org.sopt.auth.domain.TokenProvider;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginCommandService {

  private final UserReader userReader;
  private final PasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;

  public LoginServiceResponse execute(final LoginServiceRequest serviceRequest) {
    final User user = userReader.getUserByLoginIdOrThrow(serviceRequest.loginId());

    if (!passwordEncoder.matches(serviceRequest.password(), user.getPassword())) {
      throw new ApiException(ErrorCode.USER_UNAUTHORIZED);
    }

    String token = tokenProvider.createToken(user.getId());

    return LoginServiceResponse.of(user.getId(), user.getLoginId(), token);
  }
}

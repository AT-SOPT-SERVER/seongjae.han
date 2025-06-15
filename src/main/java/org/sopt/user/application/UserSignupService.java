package org.sopt.user.application;

import lombok.RequiredArgsConstructor;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.user.application.dto.request.UserServiceRequest.CreateServiceRequest;
import org.sopt.user.application.dto.response.UserServiceResponse.SignupServiceResponse;
import org.sopt.user.domain.User;
import org.sopt.user.domain.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserSignupService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  /**
   * 유저 생성 로직
   *
   * @param serviceRequest 유저 생성 리퀘스트
   * @return 생성된 유저 entity
   */
  public SignupServiceResponse execute(final CreateServiceRequest serviceRequest) {
    final String encodedPassword = passwordEncoder.encode(serviceRequest.password());

    if (userRepository.findByLoginId(serviceRequest.loginId()).isPresent()) {
      throw new ApiException(ErrorCode.DUPLICATE_USER_LOGIN_ID);
    }

    User newUser = User.createUser(serviceRequest.loginId(), encodedPassword, serviceRequest.name(), serviceRequest.email());
    User saved = userRepository.save(newUser);

    return SignupServiceResponse.from(saved);
  }
}

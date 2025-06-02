package org.sopt.user.application;

import org.sopt.user.domain.User;
import org.sopt.user.dto.UserRequest;
import org.sopt.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * 유저 생성 로직
   * @param userCreateRequest 유저 생성 dto
   * @return 생성된 유저 entity
   */
  public User saveUser(UserRequest.CreateRequest userCreateRequest) {
    User newUser = User.of(userCreateRequest.name(), userCreateRequest.email());

    return userRepository.save(newUser);
  }
}

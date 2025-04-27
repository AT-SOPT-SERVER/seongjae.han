package org.sopt.service;

import org.sopt.domain.User;
import org.sopt.dto.UserRequest;
import org.sopt.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User saveUser(UserRequest.CreateRequest userCreateRequest) {
    return userRepository.save(new User(userCreateRequest.name(), userCreateRequest.email()));
  }
}

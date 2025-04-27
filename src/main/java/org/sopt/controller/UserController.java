package org.sopt.controller;

import org.sopt.domain.User;
import org.sopt.dto.UserRequest;
import org.sopt.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final UserService userService;

  public UserController(final UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/user")
  public User save(@RequestBody final UserRequest.CreateRequest createRequest) {
    System.out.println(createRequest.name());
    System.out.println(createRequest.email());
    return userService.saveUser(createRequest);
  }
}

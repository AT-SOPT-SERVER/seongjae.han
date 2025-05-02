package org.sopt.controller;

import org.sopt.domain.User;
import org.sopt.dto.UserRequest.CreateRequest;
import org.sopt.responses.ApiResponse;
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
  public ApiResponse<User> save(@RequestBody final CreateRequest createRequest) {
    return ApiResponse.success(userService.saveUser(createRequest));
  }
}

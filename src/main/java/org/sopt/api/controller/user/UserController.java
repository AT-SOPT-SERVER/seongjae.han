package org.sopt.api.controller.user;

import org.sopt.domain.user.User;
import org.sopt.api.service.user.dto.UserRequest.CreateRequest;
import org.sopt.global.common.response.ApiResponse;
import org.sopt.api.service.user.UserService;
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

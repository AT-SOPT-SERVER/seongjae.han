package org.sopt.user.api;

import org.sopt.user.domain.User;
import org.sopt.user.dto.UserRequest.CreateRequest;
import org.sopt.global.response.ApiResponse;
import org.sopt.user.application.UserService;
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

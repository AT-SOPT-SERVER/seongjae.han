package org.sopt.user.api;

import static org.sopt.global.constants.AppConstants.API_PREFIX;

import org.sopt.user.domain.User;
import org.sopt.user.dto.UserRequest.CreateRequest;
import org.sopt.global.response.ApiResponse;
import org.sopt.user.application.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_PREFIX + "/user")
public class UserController {

  private final UserService userService;

  public UserController(final UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ApiResponse<User> save(@RequestBody final CreateRequest createRequest) {
    return ApiResponse.success(userService.saveUser(createRequest));
  }
}

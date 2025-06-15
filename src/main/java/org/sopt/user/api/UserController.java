package org.sopt.user.api;

import static org.sopt.global.constants.AppConstants.API_PREFIX;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.global.response.ApiResponse;
import org.sopt.user.api.dto.UserRequest.UserCreateRequest;
import org.sopt.user.api.dto.UserResponse;
import org.sopt.user.api.dto.UserResponse.SignupResponse;
import org.sopt.user.application.UserSignupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_PREFIX + "/auth")
@RequiredArgsConstructor
public class UserController {

  private final UserSignupService userSignupService;

  @PostMapping("/signup")
  public ResponseEntity<ApiResponse<UserResponse.SignupResponse>> signup(
      @Valid @RequestBody final UserCreateRequest userCreateRequest) {

    return ResponseEntity.ok(ApiResponse.success(
        SignupResponse.from(userSignupService.execute(userCreateRequest.toServiceRequest()))));
  }
}

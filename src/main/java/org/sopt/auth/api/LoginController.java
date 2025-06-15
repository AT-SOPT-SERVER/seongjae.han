package org.sopt.auth.api;

import lombok.RequiredArgsConstructor;
import org.sopt.auth.api.dto.LoginRequest;
import org.sopt.auth.api.dto.LoginResponse;
import org.sopt.auth.application.command.LoginCommandService;
import org.sopt.global.constants.AppConstants;
import org.sopt.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppConstants.API_PREFIX + "/auth")
@RequiredArgsConstructor
public class LoginController {

  private final LoginCommandService loginCommandService;

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {

    return ResponseEntity.ok(ApiResponse.success(
        LoginResponse.from(loginCommandService.execute(loginRequest.toServiceRequest()))));
  }

}

package org.sopt.api.service.user.dto;

import org.sopt.api.service.user.dto.UserRequest.CreateRequest;

public sealed interface UserRequest permits CreateRequest {

  record CreateRequest(String name, String email) implements UserRequest {

  }
}

package org.sopt.user.dto;

import org.sopt.user.dto.UserRequest.CreateRequest;

public sealed interface UserRequest permits CreateRequest {

  record CreateRequest(String name, String email) implements UserRequest {

  }
}

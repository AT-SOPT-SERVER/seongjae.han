package org.sopt.dto;

import org.sopt.dto.UserRequest.CreateRequest;

public sealed interface UserRequest permits CreateRequest {

  record CreateRequest(String name, String email) implements UserRequest {

  }
}

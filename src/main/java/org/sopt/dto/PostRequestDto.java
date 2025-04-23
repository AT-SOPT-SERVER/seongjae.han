package org.sopt.dto;

public record PostRequestDto() {

  public record PostCreateRequestDto(String title) {

  }

  public record PostUpdateRequestDto(Long id, String title) {

  }
}

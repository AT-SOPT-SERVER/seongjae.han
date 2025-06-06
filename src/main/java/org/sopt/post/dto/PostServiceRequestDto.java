package org.sopt.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import org.sopt.post.dto.PostServiceRequestDto.CreateServiceRequest;
import org.sopt.post.dto.PostServiceRequestDto.UpdateServiceRequest;

public sealed interface PostServiceRequestDto permits CreateServiceRequest, UpdateServiceRequest {

  @Builder(access = AccessLevel.PROTECTED)
  record CreateServiceRequest(String title, String content) implements PostServiceRequestDto {

    public static CreateServiceRequest of(String title, String content) {

      return CreateServiceRequest.builder()
          .title(title)
          .content(content)
          .build();
    }
  }

  record UpdateServiceRequest(Long id, String title, String content) implements
      PostServiceRequestDto {

  }
}

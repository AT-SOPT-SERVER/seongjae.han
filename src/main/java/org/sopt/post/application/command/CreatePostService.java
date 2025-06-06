package org.sopt.post.application.command;

import org.sopt.post.dto.PostServiceResponseDto.itemServiceResponse;
import org.sopt.post.dto.PostServiceRequestDto.CreateServiceRequest;

public interface CreatePostService {


  itemServiceResponse execute(Long userId, CreateServiceRequest createRequest);
}

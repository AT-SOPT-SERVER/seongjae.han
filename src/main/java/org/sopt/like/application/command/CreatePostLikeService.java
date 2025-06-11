package org.sopt.like.application.command;

import org.sopt.like.application.dto.LikeServiceRequestDto.CreatePostLikeServiceRequest;
import org.sopt.like.application.dto.LikeServiceResponseDto.CreatePostLikeServiceResponse;

public interface CreatePostLikeService {

  CreatePostLikeServiceResponse execute(Long userId, CreatePostLikeServiceRequest serviceRequest);
}

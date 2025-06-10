package org.sopt.like.application.command;

import org.sopt.like.application.dto.LikeServiceRequestDto.CreateCommentLikeServiceRequest;
import org.sopt.like.application.dto.LikeServiceResponseDto.CreateCommentLikeServiceResponse;

public interface CreateCommentLikeService {

  CreateCommentLikeServiceResponse execute(Long userId, CreateCommentLikeServiceRequest serviceRequest);
}

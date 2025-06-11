package org.sopt.like.application.command;

import org.sopt.like.application.dto.LikeServiceRequestDto.DeletePostLikeServiceRequest;
import org.sopt.like.application.dto.LikeServiceResponseDto.DeletePostLikeServiceResponse;

public interface DeletePostLikeService {

  DeletePostLikeServiceResponse execute(Long userId, DeletePostLikeServiceRequest serviceRequest);
}

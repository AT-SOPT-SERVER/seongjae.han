package org.sopt.like.application.command;

import org.sopt.like.application.dto.LikeServiceRequestDto.DeleteCommentLikeServiceRequest;
import org.sopt.like.application.dto.LikeServiceResponseDto.DeleteCommentLikeServiceResponse;

public interface DeleteCommentLikeService {

  DeleteCommentLikeServiceResponse execute(Long userId, DeleteCommentLikeServiceRequest serviceRequest);
}

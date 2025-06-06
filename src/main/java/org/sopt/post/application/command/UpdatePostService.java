package org.sopt.post.application.command;

import org.sopt.post.dto.PostServiceRequestDto.UpdatePostServiceRequest;
import org.sopt.post.dto.PostServiceResponseDto.PostItemServiceResponse;

public interface UpdatePostService {

  PostItemServiceResponse execute(final Long userId, UpdatePostServiceRequest updateRequest);
}

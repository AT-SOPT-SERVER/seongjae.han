package org.sopt.post.application.command;

import org.sopt.post.application.dto.PostServiceResponseDto.PostItemServiceResponse;
import org.sopt.post.application.dto.PostServiceRequestDto.CreatePostServiceRequest;

public interface CreatePostService {


  PostItemServiceResponse execute(Long userId, CreatePostServiceRequest createRequest);
}

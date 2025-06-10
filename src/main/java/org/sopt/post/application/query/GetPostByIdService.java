package org.sopt.post.application.query;

import org.sopt.post.application.dto.PostServiceResponseDto.PostItemServiceResponse;

public interface GetPostByIdService {

  public PostItemServiceResponse execute(final Long userId, final Long postId);
}

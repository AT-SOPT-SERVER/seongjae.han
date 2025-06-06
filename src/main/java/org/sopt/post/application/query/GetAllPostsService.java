package org.sopt.post.application.query;

import org.sopt.post.dto.PostServiceResponseDto.PostListServiceResponse;

public interface GetAllPostsService {

  PostListServiceResponse execute(Long userId);
}

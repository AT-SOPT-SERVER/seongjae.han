package org.sopt.post.application.query;

import org.sopt.post.application.dto.PostServiceRequestDto;
import org.sopt.post.application.dto.PostServiceRequestDto.SearchPostListServiceRequest;
import org.sopt.post.application.dto.PostServiceResponseDto.PostListServiceResponse;

public interface SearchPostsService {

  PostListServiceResponse execute(Long userId, SearchPostListServiceRequest serviceRequest);
}

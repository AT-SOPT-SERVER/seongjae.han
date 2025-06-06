package org.sopt.post.application.query;

import org.sopt.post.dto.PostServiceResponseDto.PostListServiceResponse;

public interface SearchPostsService {

  PostListServiceResponse execute(final Long userId, final PostSearchSort searchSort, final String keyword);
}

package org.sopt.post.application.query;

import lombok.RequiredArgsConstructor;
import org.sopt.post.application.dto.PostServiceRequestDto.GetAllPostsServiceRequest;
import org.sopt.post.application.dto.PostServiceResponseDto.PostListServiceResponse;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.PostQueryRepository;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetAllPostsServiceImpl implements
    GetAllPostsService {

  private final UserReader userReader;
  private final PostQueryRepository postQueryRepository;

  @Override
  public PostListServiceResponse execute(final Long userId,
      GetAllPostsServiceRequest serviceRequest) {
    final User user = userReader.getUserOrThrow(userId);
    final Pageable pageRequest = serviceRequest.toPageable();
    final Page<Post> posts = postQueryRepository.getPosts(pageRequest);

    return PostListServiceResponse.from(posts);
  }
}

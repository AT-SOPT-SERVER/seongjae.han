package org.sopt.post.application.query;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.post.application.dto.PostServiceRequestDto.SearchPostListServiceRequest;
import org.sopt.post.application.dto.PostServiceResponseDto.PostListServiceResponse.PostHeaderDto;
import org.sopt.post.application.reader.PostReader;
import org.sopt.post.domain.Post;
import org.sopt.post.application.dto.PostServiceResponseDto.PostListServiceResponse;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchPostsServiceImpl implements
    SearchPostsService {

  private final UserReader userReader;
  private final PostReader postReader;

  @Override
  public PostListServiceResponse execute(final Long userId,
      final SearchPostListServiceRequest serviceRequest) {
    final User user = userReader.getUserOrThrow(userId);

    List<Post> posts = postReader.searchPosts(serviceRequest);

    return PostListServiceResponse.from(posts);
  }
}

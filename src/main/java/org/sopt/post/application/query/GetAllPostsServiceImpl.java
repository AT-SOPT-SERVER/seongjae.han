package org.sopt.post.application.query;

import java.util.List;
import lombok.RequiredArgsConstructor;
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
public class GetAllPostsServiceImpl implements
    GetAllPostsService {

  private final PostReader postReader;
  private final UserReader userReader;

  @Override
  public PostListServiceResponse execute(final Long userId) {
    final User user = userReader.getUserOrThrow(userId);
    final List<Post> posts = postReader.getPosts();

    return PostListServiceResponse.from(posts);
  }
}

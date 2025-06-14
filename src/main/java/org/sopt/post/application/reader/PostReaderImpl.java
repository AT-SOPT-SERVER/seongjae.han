package org.sopt.post.application.reader;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.post.application.query.PostSearchSort;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.PostQueryRepository;
import org.sopt.post.domain.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostReaderImpl implements PostReader {

  private final PostRepository postRepository;
  private final PostQueryRepository postQueryRepository;

  @Override
  public Post getPostOrThrow(final Long postId) {

    return postRepository.findById(postId)
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_POST));
  }

  @Override
  public Post getPostWithCommentOrThrow(final Long postId) {

    return postRepository.findWithCommentsUsersById(postId)
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_POST));
  }

  @Override
  public boolean existsByTitle(final String title) {
    return postRepository.existsByTitle(title);
  }

  @Override
  public List<Post> getPosts() {
    return postQueryRepository.getPosts();
  }

  @Override
  public Page<Post> getPosts(final Pageable pageRequest) {
    return postQueryRepository.getPosts(pageRequest);
  }

  @Override
  public Page<Post> searchPosts(final Pageable pageable, final PostSearchSort postSearchSort,
      final String keyword) {

    return postQueryRepository.searchPosts(pageable, postSearchSort, keyword);
  }
}

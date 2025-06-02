package org.sopt.post.application.reader;

import lombok.RequiredArgsConstructor;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.PostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostReaderImpl implements PostReader{

  private final PostRepository postRepository;

  @Override
  public Post getPostOrThrow(final Long postId) {
    return postRepository.findById(postId)
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_POST));
  }
}

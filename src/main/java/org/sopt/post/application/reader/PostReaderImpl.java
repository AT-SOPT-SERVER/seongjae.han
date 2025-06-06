package org.sopt.post.application.reader;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.post.PostTag;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.PostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostReaderImpl implements PostReader {

  private final PostRepository postRepository;

  @Override
  public Post getPostOrThrow(final Long postId) {

    return postRepository.findById(postId)
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_POST));
  }

  @Override
  public boolean existsByTitle(final String title) {

    return postRepository.existsByTitle(title);
  }

  @Override
  public List<Post> getPosts() {

    return postRepository.findAll();
  }

  @Override
  public List<Post> findPostsByTitleContaining(final String keyword) {

    return postRepository.findPostsByTitleContaining(keyword);
  }

  @Override
  public List<Post> findPostsByWriterNameContaining(final String keyword) {

    return postRepository.findPostsByWriterNameContaining(keyword);
  }

  @Override
  public List<Post> findPostsByTag(final PostTag tag) {

    return postRepository.findPostsByTag(tag);
  }
}

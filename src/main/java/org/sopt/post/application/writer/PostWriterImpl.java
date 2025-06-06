package org.sopt.post.application.writer;

import lombok.RequiredArgsConstructor;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.PostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostWriterImpl implements PostWriter {

  private final PostRepository postRepository;

  @Override
  public Post save(final Post post) {
    return postRepository.save(post);
  }

  @Override
  public void delete(final Post post) {
    postRepository.delete(post);
  }
}

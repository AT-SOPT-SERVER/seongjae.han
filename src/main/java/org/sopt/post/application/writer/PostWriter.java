package org.sopt.post.application.writer;

import org.sopt.post.domain.Post;

public interface PostWriter {

  Post save(Post post);

  void delete(Post post);

}

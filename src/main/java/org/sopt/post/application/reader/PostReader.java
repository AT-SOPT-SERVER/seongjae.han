package org.sopt.post.application.reader;

import org.sopt.post.domain.Post;

public interface PostReader {

  Post getPostOrThrow(Long postId);
}

package org.sopt.post.application.reader;

import java.util.List;
import org.sopt.post.PostTag;
import org.sopt.post.domain.Post;

public interface PostReader {

  Post getPostOrThrow(Long postId);

  boolean existsByTitle(String title);

  List<Post> getPosts();

  List<Post> findPostsByTitleContaining(String keyword);

  List<Post> findPostsByWriterNameContaining(String keyword);

  List<Post> findPostsByTag(PostTag tag);
}

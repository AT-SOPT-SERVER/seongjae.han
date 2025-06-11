package org.sopt.post.application.reader;

import java.util.List;
import org.sopt.post.application.query.PostSearchSort;
import org.sopt.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostReader {

  Post getPostOrThrow(Long postId);

  Post getPostWithCommentOrThrow(Long postId);

  boolean existsByTitle(String title);

  List<Post> getPosts();

  Page<Post> getPosts(Pageable pageRequest);

  Page<Post> searchPosts(Pageable pageable, PostSearchSort postSearchSort, String keyword);
}

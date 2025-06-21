package org.sopt.post.domain;

import java.util.List;
import org.sopt.post.application.query.PostSearchSort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostQueryRepository {

  List<Post> getPosts();

  Page<Post> getPosts(Pageable pageRequest);

  Page<Post> searchPosts(Pageable pageable, PostSearchSort postSearchSort, String keyword);
}

package org.sopt.post.application.reader;

import java.util.List;
import org.sopt.post.application.dto.PostServiceRequestDto.SearchPostListServiceRequest;
import org.sopt.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostReader {

  Post getPostOrThrow(Long postId);

  Post getPostWithCommentOrThrow(Long postId);

  boolean existsByTitle(String title);

  List<Post> getPosts();

  List<Post> searchPosts(SearchPostListServiceRequest serviceRequest);

  Page<Post> getPosts(Pageable pageRequest);
}

package org.sopt.post.application.reader;

import java.util.List;
import org.sopt.post.application.dto.PostServiceRequestDto.SearchPostListServiceRequest;
import org.sopt.post.domain.Post;

public interface PostReader {

  Post getPostOrThrow(Long postId);

  boolean existsByTitle(String title);

  List<Post> getPosts();

  List<Post> searchPosts(SearchPostListServiceRequest serviceRequest);
}

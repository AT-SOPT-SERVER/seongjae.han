package org.sopt.post.application.reader;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.post.PostTag;
import org.sopt.post.application.query.PostSearchSort;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.PostRepository;
import org.sopt.post.domain.QPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostReaderImpl implements PostReader {

  private final PostRepository postRepository;
  private final JPAQueryFactory queryFactory;

  @Override
  public Post getPostOrThrow(final Long postId) {

    return postRepository.findById(postId)
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_POST));
  }

  @Override
  public Post getPostWithCommentOrThrow(final Long postId) {

    return postRepository.findWithCommentsUsersById(postId)
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_POST));
  }

  @Override
  public boolean existsByTitle(final String title) {
    return postRepository.existsByTitle(title);
  }

  @Override
  public List<Post> getPosts() {
    QPost post = QPost.post;
    return queryFactory.selectFrom(post)
        .fetch();
  }

  @Override
  public Page<Post> getPosts(final Pageable pageRequest) {
    return postRepository.findAll(pageRequest);
  }


  @Override
  public Page<Post> searchPosts(final Pageable pageable, final PostSearchSort postSearchSort,
      final String keyword) {
    return switch (postSearchSort) {
      case POST_TITLE -> postRepository.findPostsByTitleContaining(keyword, pageable);
      case WRITER_NAME -> postRepository.findPostsByWriterNameContaining(keyword, pageable);
      case POST_TAG -> {
        PostTag tag = PostTag.from(keyword);
        yield postRepository.findPostsByTag(tag, pageable);
      }
    };
  }
}

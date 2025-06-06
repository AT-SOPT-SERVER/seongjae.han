package org.sopt.post.application;

import java.util.List;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.PostServiceResponseDto.PostListServiceResponse;
import org.sopt.post.application.query.PostSearchSort;
import org.sopt.post.PostTag;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.post.domain.PostRepository;
import org.sopt.user.domain.UserRepository;
import org.sopt.global.util.TimeIntervalUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class PostApiService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final TimeIntervalUtil postTimeIntervalUtil;

  public PostApiService(
      PostRepository postRepository,
      UserRepository userRepository,
      TimeIntervalUtil postTimeIntervalUtil
  ) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
    this.postTimeIntervalUtil = postTimeIntervalUtil;
  }

  /**
   * 게시물 삭제
   *
   * @param postId 삭제할 게시물 아이디
   */
  @Transactional
  public void deletePostById(final Long postId) {
    Post post = postRepository.findFirstById(postId)
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_POST));

    postRepository.deleteById(post.getId());
  }

  /**
   * 게시물 제목으로 검색 (like %keyword%)
   *
   * @param keyword 검색 키워드
   * @return 게시물 리스트
   */
  @Transactional(readOnly = true)
  public PostListServiceResponse searchPostsByKeyword(
      final PostSearchSort searchSort,
      final String keyword
  ) {

    if (keyword.isBlank()) {
      return new PostListServiceResponse(List.of());
    }

    List<Post> posts = getPosts(searchSort, keyword);

    return new PostListServiceResponse(posts.stream()
        .map(post -> new PostListServiceResponse.PostHeaderDto(post.getTitle(), post.getUser().getName()))
        .toList());
  }


  /**
   * 게시물을 검색하는 메서드입니다.
   *
   * @param searchSort 검색 종류 (작성자, 제목)
   * @param keyword 검색어
   * @return 검색된 게시물 리스트 (생성 내림차순)
   */
  private List<Post> getPosts(final PostSearchSort searchSort, final String keyword) {

    return switch (searchSort) {
      case POST_TITLE -> postRepository.findPostsByTitleContaining(keyword);
      case WRITER_NAME -> postRepository.findPostsByWriterNameContaining(keyword);
      case POST_TAG -> {
        PostTag tag = PostTag.from(keyword);
        yield postRepository.findPostsByTag(tag);
      }
    };
  }
}

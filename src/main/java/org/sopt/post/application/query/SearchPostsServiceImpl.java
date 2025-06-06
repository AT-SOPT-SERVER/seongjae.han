package org.sopt.post.application.query;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.post.PostTag;
import org.sopt.post.application.reader.PostReader;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.PostServiceResponseDto.PostListServiceResponse;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
// TODO: test 코드 작성
public class SearchPostsServiceImpl implements
    SearchPostsService {

  private final UserReader userReader;
  private final PostReader postReader;

  @Override
  public PostListServiceResponse execute(final Long userId, final PostSearchSort searchSort,
      final String keyword) {
    final User user = userReader.getUserOrThrow(userId);

    if (keyword.isBlank()) {
      return new PostListServiceResponse(List.of());
    }

    List<Post> posts = getPosts(searchSort, keyword);

    return new PostListServiceResponse(posts.stream()
        .map(post -> new PostListServiceResponse.PostHeaderDto(post.getTitle(),
            post.getUser().getName()))
        .toList());
  }

  /**
   * 게시물을 검색하는 메서드입니다.
   *
   * @param searchSort 검색 종류 (작성자, 제목)
   * @param keyword    검색어
   * @return 검색된 게시물 리스트 (생성 내림차순)
   */
  private List<Post> getPosts(final PostSearchSort searchSort, final String keyword) {

    return switch (searchSort) {
      case POST_TITLE -> postReader.findPostsByTitleContaining(keyword);
      case WRITER_NAME -> postReader.findPostsByWriterNameContaining(keyword);
      case POST_TAG -> {
        PostTag tag = PostTag.from(keyword);
        yield postReader.findPostsByTag(tag);
      }
    };
  }

}

package org.sopt.post.application.query;

import lombok.RequiredArgsConstructor;
import org.sopt.post.application.reader.PostReader;
import org.sopt.post.domain.Post;
import org.sopt.post.application.dto.PostServiceResponseDto.PostItemServiceResponse;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetPostByIdServiceImpl implements GetPostByIdService {

  private final UserReader userReader;
  private final PostReader postReader;

  /**
   * 게시물 아이디로 검색
   *
   * @param userId 유저 아이디
   * @param postId 게시물 아이디
   * @return 게시물 response item dto
   */
  @Override
  public PostItemServiceResponse execute(final Long userId, final Long postId) {
    final User user = userReader.getUserOrThrow(userId);
    final Post post = postReader.getPostWithCommentOrThrow(postId);

    return PostItemServiceResponse.from(post);
  }
}

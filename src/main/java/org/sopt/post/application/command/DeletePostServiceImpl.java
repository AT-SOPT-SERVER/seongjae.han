package org.sopt.post.application.command;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.comment.application.reader.CommentReader;
import org.sopt.comment.application.writer.CommentWriter;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.like.application.reader.LikeReader;
import org.sopt.like.application.writer.LikeWriter;
import org.sopt.like.domain.Like;
import org.sopt.post.application.reader.PostReader;
import org.sopt.post.application.writer.PostWriter;
import org.sopt.post.domain.Post;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeletePostServiceImpl implements DeletePostService {

  private final PostWriter postWriter;
  private final PostReader postReader;
  private final UserReader userReader;
  private final LikeReader likeReader;
  private final LikeWriter likeWriter;


  @Override
  public void execute(final Long userId, final Long postId) {
    final User user = userReader.getUserOrThrow(userId);
    final Post post = postReader.getPostOrThrow(postId);

    validateUserPostOwnership(user, post);
    post.clearAllComments();

    // comments의 경우 orphanRemoval 가 true이기 때문에 변경감지로 삭제하는 로직을 post 도메인 안에 캡슐화 할 수 있지만
    // like의 경우 다형성적인 테이블 구조를 가지고 있기 때문에 변경감지를 사용할 수 없어, 직접 로직을 넣어주어야 한다.
    final List<Like> likes = likeReader.getByPostId(postId);
    likeWriter.deleteBatch(likes);

    postWriter.delete(post);
  }

  private static void validateUserPostOwnership(final User user, final Post post) {
    if (!user.equals(post.getUser())) {
      throw new ApiException(ErrorCode.USER_NOT_PERMITTED);
    }
  }
}

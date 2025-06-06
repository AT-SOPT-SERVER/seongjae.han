package org.sopt.post.application.command;

import lombok.RequiredArgsConstructor;
import org.sopt.global.error.exception.ApiException;
import org.sopt.global.error.exception.ErrorCode;
import org.sopt.global.util.TimeIntervalUtil;
import org.sopt.post.application.writer.PostWriter;
import org.sopt.post.domain.Post;
import org.sopt.post.dto.PostServiceRequestDto.CreatePostServiceRequest;
import org.sopt.post.dto.PostServiceResponseDto.PostItemServiceResponse;
import org.sopt.user.application.reader.UserReader;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CreatePostServiceImpl implements
    CreatePostService {

  private final UserReader userReader;
  private final TimeIntervalUtil timeIntervalUtil;
  private final PostWriter postWriter;
  private final PostValidator postValidator;

  /**
   * 게시물 생성
   *
   * @param createRequest 게시물 생성 dto(제목, 내용)
   * @return 게시물 아이템 dto
   */
  @Override
  public PostItemServiceResponse execute(final Long userId, final CreatePostServiceRequest createRequest) {

    User user = userReader.getUserOrThrow(userId);
    throwIfInputTimeIntervalNotValid();

    Post post = Post.of(createRequest.title(), createRequest.content(), user);
    postValidator.validateDuplicateTitle(post.getTitle());

    Post saved = postWriter.save(post);

    timeIntervalUtil.startTimer();

    return new PostItemServiceResponse(saved.getTitle(), saved.getContent(),
        saved.getUser().getName());
  }

  /**
   * 게시글 작성 시간 validate 로직
   */
  private void throwIfInputTimeIntervalNotValid() {
    if (!timeIntervalUtil.isAvailable()) {
      throw new ApiException(ErrorCode.TOO_MANY_POST_REQUESTS);
    }
  }
}

package org.sopt.comment.application.query;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.application.reader.CommentReader;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.application.dto.CommentServiceRequestDto.CommentListServiceRequestDto;
import org.sopt.comment.application.dto.CommentServiceResponseDto.CommentListDto;
import org.sopt.post.application.reader.PostReader;
import org.sopt.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetCommentListServiceImpl implements GetCommentListService {

  private final PostReader postReader;
  private final CommentReader commentReader;

  @Override
  public CommentListDto execute(final CommentListServiceRequestDto commentListRequestDto) {
    final Post post = postReader.getPostOrThrow(commentListRequestDto.postId());

    final Pageable pageRequest = commentListRequestDto.toPageable();

    final Page<Comment> commentPage = commentReader.getCommentsByPostId(
        commentListRequestDto.postId(),
        pageRequest
    );

    // 최종 응답 DTO 생성
    return CommentListDto.from(commentPage);
  }
}

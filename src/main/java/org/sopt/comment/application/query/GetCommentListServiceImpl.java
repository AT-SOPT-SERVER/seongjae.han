package org.sopt.comment.application.query;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.comment.application.reader.CommentReader;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.application.dto.CommentServiceRequestDto.CommentListServiceRequestDto;
import org.sopt.comment.application.dto.CommentServiceResponseDto.CommentItemDto;
import org.sopt.comment.application.dto.CommentServiceResponseDto.CommentListDto;
import org.sopt.post.application.reader.PostReader;
import org.sopt.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCommentListServiceImpl implements GetCommentListService {

  private final PostReader postReader;
  private final CommentReader commentReader;

  @Override
  public CommentListDto execute(final CommentListServiceRequestDto commentListRequestDto) {
    final Post post = postReader.getPostOrThrow(commentListRequestDto.postId());

    final Pageable pageRequest = getPageable(commentListRequestDto);

    final Page<Comment> commentPage = commentReader.getCommentsByPostId(
        commentListRequestDto.postId(),
        pageRequest
    );

    // 최종 응답 DTO 생성
    return CommentListDto.from(commentPage);
  }

  private Pageable getPageable(final CommentListServiceRequestDto commentListRequestDto) {
    Sort sort = Sort.by(Direction.DESC, "createdAt");

    // 오래된 댓글부터 보고 싶은 경우
    if ("asc".equalsIgnoreCase(commentListRequestDto.sortDirection())) {
      sort = Sort.by(Direction.ASC, "createdAt");
    }

    return PageRequest.of(commentListRequestDto.page(),
        commentListRequestDto.size(), sort);
  }
}

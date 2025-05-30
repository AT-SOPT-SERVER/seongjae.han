package org.sopt.api.service.comment;

import org.sopt.api.service.comment.dto.CommentRequestDto.CommentCreateRequestDto;
import org.sopt.api.service.comment.dto.CommentResponseDto.CommentItemDto;
import org.springframework.stereotype.Service;

@Service
public class CommentApiServiceImpl implements CommentApiService {

  @Override
  public CommentItemDto createComment(final CommentCreateRequestDto commentCreateRequestDto) {
    return null;
  }
}

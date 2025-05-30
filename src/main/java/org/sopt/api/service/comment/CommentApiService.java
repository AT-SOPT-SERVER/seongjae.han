package org.sopt.api.service.comment;

import org.sopt.api.service.comment.dto.CommentRequestDto.CommentCreateRequestDto;
import org.sopt.api.service.comment.dto.CommentResponseDto.CommentItemDto;

public interface CommentApiService {

  CommentItemDto createComment(CommentCreateRequestDto commentCreateRequestDto);
}

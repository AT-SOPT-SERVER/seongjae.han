package org.sopt.comment.application.command;

import org.sopt.comment.dto.CommentRequestDto.CommentCreateRequestDto;
import org.sopt.comment.dto.CommentResponseDto.CommentItemDto;

public interface CreateCommentService {

  CommentItemDto execute(final CommentCreateRequestDto commentCreateRequestDto);
}

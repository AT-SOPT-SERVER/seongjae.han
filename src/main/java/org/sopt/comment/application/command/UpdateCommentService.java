package org.sopt.comment.application.command;

import org.sopt.comment.dto.CommentRequestDto.CommentUpdateRequestDto;
import org.sopt.comment.dto.CommentResponseDto.CommentItemDto;

public interface UpdateCommentService {

  CommentItemDto execute(final CommentUpdateRequestDto commentUpdateRequestDto);
}

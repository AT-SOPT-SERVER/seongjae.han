package org.sopt.comment.application.command;

import org.sopt.comment.application.dto.CommentServiceRequestDto.CommentUpdateServiceRequestDto;
import org.sopt.comment.application.dto.CommentServiceResponseDto.CommentItemDto;

public interface UpdateCommentService {

  CommentItemDto execute(final CommentUpdateServiceRequestDto commentUpdateRequestDto);
}

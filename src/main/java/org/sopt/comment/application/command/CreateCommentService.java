package org.sopt.comment.application.command;

import org.sopt.comment.application.dto.CommentServiceRequestDto.CommentCreateServiceRequestDto;
import org.sopt.comment.application.dto.CommentServiceResponseDto.CommentItemDto;

public interface CreateCommentService {

  CommentItemDto execute(final CommentCreateServiceRequestDto commentCreateRequestDto);
}
